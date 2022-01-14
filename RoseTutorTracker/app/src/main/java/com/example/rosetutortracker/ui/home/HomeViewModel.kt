package com.example.rosetutortracker.ui.home

import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor

class HomeViewModel: FindTutorViewModel()  {
    fun containsTutor(tutor: Tutor): Boolean{
        return this.tutors.contains(tutor)
    }

}