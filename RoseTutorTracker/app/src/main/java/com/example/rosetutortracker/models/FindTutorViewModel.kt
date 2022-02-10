package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.abstracts.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class FindTutorViewModel : BaseViewModel<Tutor>() {
    private var refStudents = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
    private var refTutors = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)

    open fun addTutor(tutor: Tutor?) {
//        val newTutor = tutor ?: createRandomTutor()
//        Log.d("rr", "adding ${newTutor.name}")
//        ref.add(newTutor)
    }

//    private fun createRandomTutor(): Tutor {
//        val name = "Name${Random.nextInt(500)}"
//        val email = "$name@rose-hulman.edu"
//        val classYear = Random.nextInt(2022, 2026)
//        return Tutor(name, email, classYear, Random.nextBoolean())
//    }

    fun findTutor(searchBy: Int, searchTerm: String, function: () -> Unit) {
        when (searchBy) {
            0 -> {
                val newSearchTerm = toTitle(searchTerm)
                refStudents.whereGreaterThanOrEqualTo("name", newSearchTerm)
                    .whereLessThanOrEqualTo("name", newSearchTerm + 'z')
                    .whereEqualTo("tutor", true)

                    .get()
                    .addOnCompleteListener { call ->
                        call.result?.documents?.forEach {
                            Log.d("rr", "Found by name $it")
                            if (it.id != Firebase.auth.uid!!){
                                val student = Student.from(it)
                                Log.d("rr", student.toString())
                                searchTutor(it.id, function, student)
                            }
                        }
                        function()
                    }
            }
            1 -> {
                val newSearchTerm = searchTerm.uppercase()
                refTutors.whereArrayContains("courses", newSearchTerm)
                    .get()
                    .addOnCompleteListener { call ->
                        call.result?.documents?.forEach {
                            if (it.id != Firebase.auth.uid!!){
                                Log.d(Constants.TAG, "Course search found: ${it}")
                                val tutor = Tutor.from(it)
                                searchStudent(it.id, function, tutor)
                            }
                        }
                    }
            }
        }
    }

    fun clearTutors() {
        list.clear()
    }

    private fun searchTutor(id: String, function: () -> Unit, student: Student) {
        refTutors.document(id).get()
            .addOnCompleteListener { doc ->
                if (doc.isSuccessful) {
                    val tutor = Tutor.from(doc.result!!)
                    Log.d("rr", "tutor: $tutor")
                    tutor.studentInfo = student
                    list.add(tutor)
                } else {
                    Log.d("rr", "error happened")
                }
                function()
            }
    }

    private fun searchStudent(id: String, function: () -> Unit, tutor: Tutor) {
        refStudents.document(id).get()
            .addOnCompleteListener { doc ->
                if (doc.isSuccessful && doc.result?.exists() == true) {
                    val student = Student.from(doc.result!!)
                    Log.d("rr", "tutor: $tutor")
                    tutor.studentInfo = student
                    list.add(tutor)
                } else {
                    Log.d("rr", "error happened")
                }
                function()
            }
    }

    private fun toTitle(searchTerm: String): String {
        val terms = searchTerm.split(" ").toMutableList()
        var newSearchTerm = ""
        for (i in 0 until terms.size) {
            terms[i] = terms[i].lowercase()
            Log.d("rr", "lowercase: ${terms[i]}")
            terms[i] = terms[i][0].uppercase() + terms[i].substring(1)
            Log.d("rr", "uppercase: ${terms[i]}")
            newSearchTerm += terms[i]
        }
        return newSearchTerm
    }
}