package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentHelpViewModel : BaseViewModel<StudentRequests>() {
    private lateinit var ref: CollectionReference
    private val subscriptions = HashMap<String, ListenerRegistration>()
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
        Log.d("resolve request", "resolving request at position $currPos")
        removeCurrent()
    }

    fun addListener(fragmentName: String, function: () -> Unit) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_MESSAGE)
        val subscription = ref.whereEqualTo("receiver", Firebase.auth.uid!!)
            .orderBy(StudentRequests.CREATED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener{ snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                error?.let{
                    Log.d(Constants.TAG, "Error: $error")
                    return@addSnapshotListener
                }
                list.clear()
                snapshot?.documents?.forEach {
                    list.add(StudentRequests.from(it))
                }
                function()
            }
        subscriptions[fragmentName] = subscription
    }

    fun removeListener(fragmentName: String) {
        subscriptions[fragmentName]?.remove()
    }
}