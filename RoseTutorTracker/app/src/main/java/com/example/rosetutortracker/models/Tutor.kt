package com.example.rosetutortracker.models

import kotlin.random.Random

class Tutor(){
    lateinit var name: String
    var available: Boolean = false
    lateinit var email: String
    var classYear: Int = 0
    private lateinit var courses: ArrayList<String>
     var isFavorite: Boolean = false

    constructor(name: String, available: Boolean) : this() {
        this.name = name
        this.available = available
        this.email = "$name@rose-hulman.edu"
        this.classYear = Random.nextInt(2022, 2026)

    }

    constructor(name: String, email: String, classYear: Int, courses: ArrayList<String>, available: Boolean) : this() {
        this.name = name
        this.email = email
        this.classYear = classYear
        this.courses = courses
        this.available = available
    }
}
