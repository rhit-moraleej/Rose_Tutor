package com.example.rosetutortracker.models

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
        var total: Double = this.overRating * numRatings
        this.numRatings++
        total += rating
        this.overRating = total / this.numRatings
    }

    fun checkAvailability(): Boolean {
        val date = LocalDateTime.now()
        val currTime = LocalTime.now()
        Log.d("Date", "${date.dayOfWeek}")
        Log.d(
            "Date",
            "Index of the day:${TutorDate.Day.valueOf(date.dayOfWeek.toString()).dayOfWeek}."
        )
        val index = TutorDate.Day.valueOf(date.dayOfWeek.toString()).dayOfWeek
        val start = String.format("%02d:%02d:00", days[index!!].startHour, days[index].startMin)
        val end = String.format("%02d:%02d:00", days[index].endHour, days[index].endMin)
        Log.d("Time", "start: $start, end: $end")
        val startTime = LocalTime.parse(start)
        val endTime = LocalTime.parse(end)
        Log.d("Time", "Tutor workday: ${days[index].working}")
        Log.d("Time", "After start time: ${currTime.isAfter(startTime)}")
        Log.d("Time", "Before end time: ${currTime.isBefore(endTime)}")
        return days[index].working && currTime.isAfter(startTime) && currTime.isBefore(endTime)
    }

    fun coursesToString(): String {
        val message = StringBuilder()
        message.append("Courses: \n")
        for (i in courses) {
            message.append("\t\t\t• $i\n")
        }
        return message.toString()
    }

    fun availabilityToString(): String {
        val message = StringBuilder()
        message.append("Available: \n")
        for (day in days){
            if (day.working){
                val length = 30 - day.day.length
                val start = String.format("%s:%02d", day.startHour, day.startMin)
                val end = String.format("%s:%02d", day.endHour, day.endMin)
                val line = String.format("%-$length.20s%5s - %s\n", "${day.day}:", start, end)
                message.append("\t\t\t• $line")
            }
        }

        return message.toString()
    }

    companion object {
        fun from(snapshot: DocumentSnapshot): Tutor {
            val tutor = snapshot.toObject(Tutor::class.java)!!
            tutor.id = snapshot.id
            return tutor
        }
    }
}

fun daySetup(): ArrayList<TutorDate> {
    val days = ArrayList<TutorDate>()
    val dayEums = TutorDate.Day.values()
    for (i in 0 until 7) {
        days.add(TutorDate(day = dayEums[i].dayPrint!!))
    }
    return days
}