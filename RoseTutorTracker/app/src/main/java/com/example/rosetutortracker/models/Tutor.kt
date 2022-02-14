package com.example.rosetutortracker.models

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime

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

    fun availabilityToString(): String {
        val message = StringBuilder()
        message.append("Available: \n")
        for (day in days){
            if (day.working){
                val start = String.format("%s:%02d", day.startHour, day.startMin)
                val end = String.format("%s:%02d", day.endHour, day.endMin)
                message.append("\t\t\t• ${day.day}: \t$start - $end \n")
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