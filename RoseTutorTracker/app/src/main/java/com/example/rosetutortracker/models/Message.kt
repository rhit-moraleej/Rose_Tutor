@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package com.example.rosetutortracker.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class StudentRequests(
    val message: String = "",
    val receiver: String = "",
    val sender: String = "",
    val senderName: String = "",
    val receiverName: String = ""
) {

    @ServerTimestamp
    var created: Timestamp? = null
    var notified = false
    @get:Exclude
    var id: String = ""

    companion object {
        const val CREATED_KEY = "created"
        fun from(snapshot: DocumentSnapshot): StudentRequests {
            val message = snapshot.toObject(StudentRequests::class.java)!!
            message.id = snapshot.id
            return message
        }
    }
}