package com.example.rosetutortracker.models

import android.content.Context
import android.util.Log
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.abstracts.BaseViewModel
import com.example.rosetutortracker.utils.NotificationUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentHelpViewModel : BaseViewModel<StudentRequests>() {
    private lateinit var ref: CollectionReference
    private val subscriptions = HashMap<String, ListenerRegistration>()
    var firstTime = true

    fun resolveCurrentStudent() {
        Log.d("notify", Firebase.auth.uid!!)
        Log.d("notify", list[currPos].senderName)
        Log.d("notify", list[currPos].message)
        ref.whereEqualTo("receiver", Firebase.auth.uid!!)
            .whereEqualTo("senderName", list[currPos].senderName)
            .whereEqualTo("message", list[currPos].message)
            .get().addOnSuccessListener {
                it.forEach { queryDocumentSnapshot ->
                    Log.d("notify", queryDocumentSnapshot.toString())
                    queryDocumentSnapshot.reference.delete()
                }
            }
        Log.d("resolve request", "resolving request at position $currPos")
        removeCurrent()
    }

    fun addListener(fragmentName: String, requireContext: Context, function: () -> Unit) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_MESSAGE)
        val subscription = ref.whereEqualTo("receiver", Firebase.auth.uid!!)
            .orderBy(StudentRequests.CREATED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
                error?.let {
                    Log.d(Constants.TAG, "Error: $error")
                    return@addSnapshotListener
                }
                list.clear()
                snapshot?.documents?.forEach {
                    list.add(StudentRequests.from(it))
                }
                snapshot?.documentChanges?.forEach {
                    Log.d("notify", it.type.toString())
                    Log.d("notify", it.document.data.toString())
                    if (it.document.data.get("receiver") == Firebase.auth.uid && !firstTime && it.type.toString() == "ADDED") {

                        NotificationUtils.createAndLaunch(
                            requireContext,
                            "${it.document.data.get("message")}",
                            "${it.document.data.get("senderName")} needs help:"
                        )

                    }
                }
                firstTime = false
                function()
            }
        subscriptions[fragmentName] = subscription
    }

    fun removeListener(fragmentName: String) {
        subscriptions[fragmentName]?.remove()
    }
}