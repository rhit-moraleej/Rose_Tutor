package com.example.rosetutortracker.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlin.random.Random

data class Tutor(var name: String = "",
            var email: String = "",
            var classYear: Int = 0,
            var available: Boolean = false,
            var courses: ArrayList<String> = getRandomCourses(Random.nextInt(1,5)),
            var isFavorite: Boolean = false,
            var overRating: Double = Random.nextDouble(0.0,5.0),
            var numRatings: Int = Random.nextInt(0, 100)){

    @get:Exclude
    var id = ""

    fun addRating(rating: Double){
        val total: Double = this.overRating * numRatings
        this.numRatings++
        this.overRating = total/this.numRatings
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
        const val COLLECTION_BY_NAME = "Tutors"
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
        fun from(snapshot: DocumentSnapshot): Tutor{
            val tutor = snapshot.toObject(Tutor:: class.java)!!
            tutor.id = snapshot.id
            return tutor
        }
    }
}

fun getRandomCourses(num: Int): ArrayList<String> {
    val randomCourses = ArrayList<String>()
    for (i in 0 until num){
        val index = Random.nextInt(Tutor.departments.size)
        val s = Tutor.departments[index] + "${Random.nextInt(100, 500)}"
        randomCourses.add(s)
    }
    return randomCourses
}

