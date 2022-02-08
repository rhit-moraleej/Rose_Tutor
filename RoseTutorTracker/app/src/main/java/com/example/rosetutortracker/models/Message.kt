package com.example.rosetutortracker.models

import com.google.firebase.firestore.DocumentSnapshot

data class StudentRequests(
    val message: String = "",
    val receiver: String = "",
    val sender: String = "",
    val senderName: String = ""
) {

    companion object {
        fun from(snapshot: DocumentSnapshot): StudentRequests {
            val message = snapshot.toObject(StudentRequests::class.java)!!
            return message
        }
    }
}