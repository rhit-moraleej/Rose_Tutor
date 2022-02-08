package com.example.rosetutortracker.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlin.random.Random

data class Tutor(
    var available: Boolean = false,
    var courses: ArrayList<String> = ArrayList(),
    var location: String = "",
    var hasCompletedSetup: Boolean = false,
    var overRating: Double = 0.0,
    var numRatings: Int = 0,
    var days: ArrayList<Boolean> = arrayListOf(false, false, false, false, false, false, false),
    var startHours: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0),
    var startMinutes: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0),
    var endHours: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0),
    var endMinutes: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0)
) {

    @get:Exclude
    var id = ""

    @get:Exclude
    var studentInfo = Student()

    fun addRating(rating: Double) {
        val total: Double = this.overRating * numRatings
        this.numRatings++
        this.overRating = total / this.numRatings
    }

    fun coursesToString(): String {
        val message = StringBuilder()
        message.append("Courses: \n")
        for (i in courses) {
            message.append("\t\t\t• $i\n")
        }
        return message.toString()
    }

    companion object {
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

        fun from(snapshot: DocumentSnapshot): Tutor {
            val tutor = snapshot.toObject(Tutor::class.java)!!
            tutor.id = snapshot.id
            return tutor
        }
    }
}

fun getRandomCourses(num: Int): ArrayList<String> {
    val randomCourses = ArrayList<String>()
    for (i in 0 until num) {
        val index = Random.nextInt(Tutor.departments.size)
        val s = Tutor.departments[index] + "${Random.nextInt(100, 500)}"
        randomCourses.add(s)
    }
    return randomCourses
}

