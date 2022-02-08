package com.example.rosetutortracker.setup

import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.edit.ChangeCourseFragment
import com.example.rosetutortracker.models.Tutor

class CourseTutorSetupFragment : ChangeCourseFragment() {
    override fun setupDoneButton() {
        binding.completeBtn.setOnClickListener {
//            tutorModel.updateTutorCourses(adapter.model.list)
//            setTempTutor()
            tutorModel.tutorTemp = Tutor()
            tutorModel.setTempCourses(adapter.model.list)
            findNavController().navigate(R.id.nav_location_tutor_setup)
        }
        binding.completeBtn.text = getString(R.string.next)
    }
//    fun setTempTutor(){
//        tutorModel.tutorTemp = Tutor()
//    }
}