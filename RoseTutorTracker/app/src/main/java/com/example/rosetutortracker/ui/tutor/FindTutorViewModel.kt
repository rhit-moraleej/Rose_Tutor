package com.example.rosetutortracker.ui.tutor

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class FindTutorViewModel : ViewModel() {
    private var tutors = ArrayList<Tutor>()
    var currPos = 0

    fun getTutorAt(pos: Int) = tutors[pos]
    fun getCurrentTutor() = tutors[currPos]
    fun addTutor(tutor: Tutor?){
        val random = Random.nextInt(500)
        val newTutor = tutor ?: Tutor("Name$random")
        tutors.add(newTutor)
    }

    fun removeCurrentTutor(){
        tutors.removeAt(currPos)
        currPos = 0
    }

    fun clearTutors(){
        tutors.clear()
    }

    fun size() = tutors.size
}