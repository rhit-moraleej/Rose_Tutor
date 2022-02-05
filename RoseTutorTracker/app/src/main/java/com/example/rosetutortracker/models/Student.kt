package com.example.rosetutortracker.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class Student(var name: String = "",
                   var email: String = "",
                   var classYear: Int = 0,
                   var favoriteTutors: ArrayList<String> = ArrayList(),
                   var isTutor: Boolean = false,
                   var hasCompletedSetup: Boolean = false) {
    @get:Exclude
    var id = ""

    companion object{
        fun from(snapshot: DocumentSnapshot): Student {
            return snapshot.toObject(Student::class.java)!!
        }
    }
}