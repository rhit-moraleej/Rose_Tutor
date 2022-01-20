package com.example.rosetutortracker.models

import kotlin.random.Random

open class Student() {
    lateinit var name: String
    lateinit var email: String
    var classYear: Int = 0

    constructor(name: String) : this() {
        this.name = name
        this.email = "$name@rose-hulman.edu"
        this.classYear = Random.nextInt(2022, 2026)
    }

    constructor(name: String, email: String, classYear: Int) : this() {
        this.name = name
        this.email = email
        this.classYear = classYear
    }
}