package com.example.rosetutortracker.models

import java.lang.StringBuilder
import kotlin.random.Random

class Tutor: Student{
    var available: Boolean = false
    private var courses: ArrayList<String>
    var isFavorite: Boolean = false
    var overRating: Double = 0.0
    var numRatings: Int = 0

    constructor(name: String, available: Boolean) : super(name) {
        this.available = available
        this.courses = ArrayList()
        this.courses.addAll(getRandomCourses(Random.nextInt(1,5)))
        this.overRating = Random.nextDouble(0.0,5.0)
        this.numRatings = Random.nextInt(0, 100)
    }

    constructor(name: String, email: String, classYear: Int, courses: ArrayList<String>, available: Boolean) : super(name, email, classYear) {
        this.courses = courses
        this.available = available
    }

    fun addRating(rating: Double){
        val total: Double = this.overRating * numRatings
        this.numRatings++
        this.overRating = total/this.numRatings
    }

    private fun getRandomCourses(num: Int): ArrayList<String> {
        val randomCourses = ArrayList<String>()
        for (i in 0 until num){
            val index = Random.nextInt(departments.size)
            val s = departments[index] + "${Random.nextInt(100, 500)}"
            randomCourses.add(s)
        }
        return randomCourses
    }

    fun coursesToString(): String{
        val message = StringBuilder()
        message.append("Courses: \n")
        for(i in courses){
            message.append("\t\t• $i\n")
        }
        return message.toString()
    }

    companion object{
        val departments = arrayListOf(
            "ANTHS",
            "BE",
            "BIO",
            "CE",
            "CHEM",
            "CSSE",
            "ECE",
            "HISTH",
            "MA",
            "PH"
        )
    }
}
