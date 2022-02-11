package com.example.rosetutortracker.models

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

data class Tutor(
    var available: Boolean = false,
    var courses: ArrayList<String> = ArrayList(),
    var location: String = "",
    var hasCompletedSetup: Boolean = false,
    var overRating: Double = 0.0,
    var numRatings: Int = 0,
    var days: ArrayList<TutorDate> = daySetup()
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

    fun checkAvailabilaty(): Boolean{
        val date = LocalDateTime.now()
        Log.d("Date", "${date.dayOfWeek}")
        return false
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

fun daySetup(): ArrayList<TutorDate> {
    val days = ArrayList<TutorDate>()
    for (i in 0 until 7 ){
        days.add(TutorDate())
    }
    return days
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

