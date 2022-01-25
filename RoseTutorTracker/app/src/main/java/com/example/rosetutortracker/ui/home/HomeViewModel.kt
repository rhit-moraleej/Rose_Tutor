package com.example.rosetutortracker.ui.home

import android.util.Log
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor

class HomeViewModel: FindTutorViewModel()  {
    override fun addTutor(tutor: Tutor?){
        val newTutor = tutor ?: createRandomTutor()
        tutors.add(newTutor)
        Log.d("rr", "adding ${newTutor.name}")
//        ref.add(newTutor)
    }
    fun containsTutor(tutor: Tutor): Boolean{
        return this.tutors.contains(tutor)
    }

}