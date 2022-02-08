package com.example.rosetutortracker.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorSetupBinding
import com.example.rosetutortracker.models.CourseViewModel
import com.example.rosetutortracker.models.TutorViewModel


class TutorSetupFragment : Fragment() {
    private lateinit var binding: FragmentTutorSetupBinding
    private lateinit var tutorModel: TutorViewModel
    private lateinit var courseModel: CourseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorSetupBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        courseModel = ViewModelProvider(requireActivity())[CourseViewModel::class.java]
        binding.updateProfileButton.setOnClickListener {
            tutorModel.completeSetup(true)
            courseModel.list = tutorModel.tutor?.courses!!
            findNavController().navigate(R.id.nav_tutor_home)
        }
        return binding.root
    }
}