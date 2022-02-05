package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class FindTutorViewModel : BaseViewModel<Tutor>() {
    private var refStudents = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
    private var refTutors = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
    private var refCourse = Firebase.firestore.collection(Constants.COLLECTION_BY_COURSE_NAME)


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
//                            list.add(Tutor.from(it))
                            searchTutor(it.id, function)
                            getTutorStudentInfo(it.id)
                        }
//                        function()
                    }
            }
            1 -> {
                val newSearchTerm = searchTerm.uppercase()
                refCourse.whereGreaterThanOrEqualTo("name", newSearchTerm)
                    .whereLessThanOrEqualTo("name", newSearchTerm + 'z')
                    .get()
                    .addOnCompleteListener { call ->
                        Log.d("rr", "results size: ${call.result?.documents?.size}")
                        call.result?.documents?.forEach {
                            Log.d("rr", "found: $it")
                            val results: String = it.data?.get("tutors").toString()
                            val tutorId = results.substring(1, results.length - 1).split(", ")
                            for (i in tutorId) {
                                Log.d("rr", i)
                                searchTutor(i, function)
                                getTutorStudentInfo(i)
                            }
                        }
                    }
            }
        }
    }

    fun clearTutors() {
        list.clear()
    }

    private fun searchTutor(id: String, function: () -> Unit) {
        refTutors.document(id).get()
            .addOnCompleteListener { doc ->
                if (doc.isSuccessful) {
                    val tutor = doc.result!!
                    Log.d("rr", "tutor: $tutor")
                    list.add(Tutor.from(tutor))
                } else {
                    Log.d("rr", "error happened")
                }
                function()
            }
    }

    private fun getTutorStudentInfo(id: String){
        refStudents.document(id).get()
            .addOnCompleteListener { doc ->
                if (doc.isSuccessful){
                    val student = doc.result!!
                    Log.d(Constants.TAG, "studentInfo: $student")
                    Log.d(Constants.TAG, "List size: ${list.size}")
                    getCurrent().studentInfo = Student.from(student)
                }else{
                    Log.d("rr", "error happened")
                }

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