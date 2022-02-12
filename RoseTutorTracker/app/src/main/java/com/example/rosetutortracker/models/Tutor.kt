package com.example.rosetutortracker.models

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime
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

    fun checkAvailability(): Boolean {
        val date = LocalDateTime.now()
        Log.d("Date", "${date.dayOfWeek}")
        Log.d(
            "Date",
            "Index of the day:${TutorDate.Day.valueOf(date.dayOfWeek.toString()).dayOfWeek}."
        )
        val index = TutorDate.Day.valueOf(date.dayOfWeek.toString()).dayOfWeek
        val hour = LocalDateTime.now().hour
        val min = LocalDateTime.now().minute
        Log.d("Date", "currTime: $hour : $min")
        Log.d(
            "Date",
            "Hour check:${days[index!!].startHour <= hour && hour < days[index].endHour} "
        )
        Log.d("Date", "Min check: ${days[index].startMin <= min}")
        return days[index].working && (days[index].startHour <= hour
                && hour < days[index].endHour) && (days[index].startMin <= min)
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
    for (i in 0 until 7) {
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

