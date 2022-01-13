package com.example.rosetutortracker.ui.tutor

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class FindTutorViewModel : ViewModel() {
    private var tutors = ArrayList<Tutor>()
    var currPos = 0

    fun getTutorAt(pos: Int) = tutors[pos]
    fun getCurrentTutor() = tutors[currPos]
    fun addQuote(tutor: Tutor?){
        val random = Random.nextInt(500)
        val newTutor = tutor ?: Tutor("Name$random")
        tutors.add(newTutor)
    }

    fun updatePos(pos : Int){
        currPos = pos
    }

    fun size() = tutors.size
}