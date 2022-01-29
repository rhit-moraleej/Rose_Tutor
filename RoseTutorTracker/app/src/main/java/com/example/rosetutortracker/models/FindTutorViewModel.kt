package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

open class FindTutorViewModel : BaseViewModel<Tutor>() {
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR_NAME)
    private var ref2 = Firebase.firestore.collection(Constants.COLLECTION_BY_COURSE_NAME)


    open fun addTutor(tutor: Tutor?) {
        val newTutor = tutor ?: createRandomTutor()
        Log.d("rr", "adding ${newTutor.name}")
        ref.add(newTutor)
    }

    private fun createRandomTutor(): Tutor {
        val name = "Name${Random.nextInt(500)}"
        val email = "$name@rose-hulman.edu"
        val classYear = Random.nextInt(2022, 2026)
        return Tutor(name, email, classYear, Random.nextBoolean())
    }

    fun findTutor(searchBy: Int, searchTerm: String, function: () -> Unit) {
        when (searchBy) {
            0 -> {
                val newSearchTerm = toTitle(searchTerm)
                ref.whereGreaterThanOrEqualTo("name", newSearchTerm)
                    .whereLessThanOrEqualTo("name", newSearchTerm + 'z')
                    .get()
                    .addOnCompleteListener { call ->
                        call.result?.documents?.forEach {
                            Log.d("rr", "$it")
                            list.add(Tutor.from(it))
                        }
                        function()
                    }
            }
            1 -> {
                val newSearchTerm = searchTerm.uppercase()
                ref2.whereGreaterThanOrEqualTo("name", newSearchTerm)
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
                            }
                        }
                    }
            }
        }
    }

    fun clearTutors() {
        list.clear()
    }

    private fun searchName(reference: CollectionReference, term: String, function: () -> Unit, completion: () -> Unit){
        reference.whereGreaterThanOrEqualTo("name", term)
            .whereLessThanOrEqualTo("name", term + 'z')
            .get()
            .addOnCompleteListener { call ->
                call.result?.documents?.forEach {
                    completion()
                }
            }
    }
    private fun searchTutor(id: String, function: () -> Unit) {
        ref.document(id).get()
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