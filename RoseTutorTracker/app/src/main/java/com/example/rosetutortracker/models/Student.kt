package com.example.rosetutortracker.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class Student(var name: String,
                   var email: String,
                   var classYear: Int = 0,
                   var isTutor: Boolean = false) {
    @get:Exclude
    var id = ""

//    constructor(name: String) : this() {
//        this.name = name
//        this.email = "$name@rose-hulman.edu"
//        this.classYear = Random.nextInt(2022, 2026)
//    }
//
//    constructor(name: String, email: String, classYear: Int) : this() {
//        this.name = name
//        this.email = email
//        this.classYear = classYear
//    }

    companion object{
        fun from(snapshot: DocumentSnapshot): Student {
            return snapshot.toObject(Student::class.java)!!
        }
    }
}