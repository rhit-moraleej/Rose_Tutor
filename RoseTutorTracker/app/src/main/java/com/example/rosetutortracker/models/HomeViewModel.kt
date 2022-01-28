package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor

class HomeViewModel: FindTutorViewModel()  {

    fun containsTutor(tutor: Tutor): Boolean{
        return this.tutors.contains(tutor)
    }

    override fun addTutor(tutor: Tutor?){

        if (tutor != null) {
            this.tutors.add(tutor)
        }
    }

}