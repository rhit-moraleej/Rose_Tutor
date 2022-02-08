package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentHelpViewModel : BaseViewModel<StudentRequests>() {
    private lateinit var ref: CollectionReference
    fun getRequests(function: () -> Unit) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_MESSAGE)

        ref.whereEqualTo("receiver", Firebase.auth.uid!!)
            .get()
            .addOnCompleteListener { doc ->
                doc.result?.documents?.forEach {
                    Log.d("message", it.toString())
                    val message = StudentRequests.from(it)
                    Log.d("message", "${message.message}")
                    list.add(message)
                }
                function()
            }


    }

    fun resolveCurrentStudent() {
        Log.d("notify",Firebase.auth.uid!!)
        Log.d("notify",list[currPos].senderName)
        Log.d("notify",list[currPos].message)
        ref.whereEqualTo("receiver",Firebase.auth.uid!!)
            .whereEqualTo("senderName",list[currPos].senderName)
            .whereEqualTo("message",list[currPos].message)
            .get().addOnSuccessListener {
            it.forEach { queryDocumentSnapshot ->
                Log.d("notify",queryDocumentSnapshot.toString())
                queryDocumentSnapshot.reference.delete()
            }
        }


        removeCurrent()
    }
}