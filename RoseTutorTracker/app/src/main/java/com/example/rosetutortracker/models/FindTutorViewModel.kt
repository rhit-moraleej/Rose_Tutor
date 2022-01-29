package com.example.rosetutortracker.models

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

open class  FindTutorViewModel :BaseViewModel<Tutor>() {
    private var ref = Firebase.firestore.collection(Tutor.COLLECTION_BY_NAME)
    private var ref2 = Firebase.firestore.collection("Course")

    open fun addTutor(tutor: Tutor?){
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

    fun findTutor(searchBy: Int, searchTerm: String, function: () -> Unit){
        when(searchBy){
            0 -> {
                val terms = searchTerm.split(" ").toMutableList()
                var newSearchTerm = ""
                for (i in 0 until terms.size){
                    terms[i] = terms[i].lowercase()
                    Log.d("rr", "lowercase: ${terms[i]}")
                    terms[i] = terms[i][0].uppercase() + terms[i].substring(1)
                    Log.d("rr", "uppercase: ${terms[i]}")
                    newSearchTerm += terms[i]
                }
                Log.d("rr", "New search term is: $newSearchTerm")
                ref.whereGreaterThanOrEqualTo(searchFields[0], newSearchTerm)
                    .whereLessThanOrEqualTo(searchFields[0], newSearchTerm + 'z')
                    .get()
                    .addOnCompleteListener { call ->
                        call.result?.documents?.forEach {
                            Log.d("rr", "$it")
                            list.add(Tutor.from(it))
                        }
                        function()
                    }
            }
            1 ->{
                val term = searchTerm.uppercase()
                ref2.whereGreaterThanOrEqualTo(searchFields[0], term)
                    .whereLessThanOrEqualTo(searchFields[0], term + 'z')
                    .get()
                    .addOnCompleteListener { call ->
                        Log.d("rr", "results size: ${call.result?.documents?.size}")
                        call.result?.documents?.forEach {
                            Log.d("rr", "found: $it")
                            var results: String = it.data?.get("tutors").toString()
                            Log.d("rr", "tutors: $results")
                            results = results.substring(1, results.length - 1)
                            val tutorId = results.split(", ")
                            Log.d("rr", results)
                            for ( i in tutorId){
                                Log.d("rr", i)
                                ref.document(i).get()
                                    .addOnCompleteListener { doc ->
                                        if (doc.isSuccessful){
                                            val tutor = doc.result!!
                                            Log.d("rr", "tutor: $tutor")
                                            list.add(Tutor.from(tutor))
                                        }else{
                                            Log.d("rr", "error happened")
                                        }
                                        function()
                                    }
                            }
                        }
                    }
            }
        }
    }

    fun clearTutors(){
        list.clear()
    }
    
    companion object{
        val searchFields = arrayListOf(
            "name",
            "courses"
        )
    }
}