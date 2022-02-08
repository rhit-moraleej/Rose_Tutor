package com.example.rosetutortracker.setup

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.edit.TutorEditDayTimeFragment

class TimeTutorSetupFragment : TutorEditDayTimeFragment() {
    override fun setupDoneButton() {
        binding.completeBtn.isVisible = true
        binding.completeBtn.text = "Complete Setup"
        binding.completeBtn.setOnClickListener {
            findNavController().navigate(R.id.nav_tutor_setup)
        }
    }
}