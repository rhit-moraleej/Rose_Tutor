package com.example.rosetutortracker.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp

data class StudentRequests(
    val message: String = "",
    val receiver: String = "",
    val sender: String = "",
    val senderName: String = ""
) {

    @ServerTimestamp
    var created: Timestamp? = null
    companion object {
        const val CREATED_KEY = "created"
        fun from(snapshot: DocumentSnapshot): StudentRequests {
            val message = snapshot.toObject(StudentRequests::class.java)!!
            return message
        }
    }
}