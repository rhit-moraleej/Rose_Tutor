package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel
import kotlin.random.Random

open class FindTutorViewModel : ViewModel() {
    internal var tutors = ArrayList<Tutor>()
    private var currPos = 0

    fun getTutorAt(pos: Int) = tutors[pos]
    fun getCurrentTutor() = tutors[currPos]
    fun addTutor(tutor: Tutor?){
        val random = Random.nextInt(500)
        val newTutor = tutor ?: Tutor("Name$random", Random.nextBoolean())
        tutors.add(newTutor)
    }

    fun updatePos(pos: Int){
        currPos = pos
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