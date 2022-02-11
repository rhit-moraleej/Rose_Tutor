package com.example.rosetutortracker.setup

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.edit.TutorEditDayTimeFragment
import com.example.rosetutortracker.models.Tutor

class TimeTutorSetupFragment : TutorEditDayTimeFragment() {
    override fun setupDoneButton() {
        binding.completeBtn.isVisible = true
        binding.completeBtn.text = "Complete Setup"
        binding.completeBtn.setOnClickListener {
            tutorModel.tutorTemp!!.days = updatedTutor.days
            findNavController().navigate(R.id.nav_tutor_setup)
        }
    }

    override fun updateTutor(){
        updatedTutor = Tutor(
            courses = tutorModel.tutorTemp?.courses!!,
            location = tutorModel.tutorTemp?.location!!,
        )
    }
}