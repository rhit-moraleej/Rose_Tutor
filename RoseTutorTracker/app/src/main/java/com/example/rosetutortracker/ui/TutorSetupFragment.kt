package com.example.rosetutortracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorSetupBinding
import com.example.rosetutortracker.models.TutorViewModel


class TutorSetupFragment : Fragment() {
    private lateinit var binding: FragmentTutorSetupBinding
    private lateinit var tutorModel: TutorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorSetupBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        binding.updateProfileButton.setOnClickListener {
            val courses = binding.courses.text.toString()
            val location = binding.location.text.toString()
            tutorModel.update(courses, location, true)
            findNavController().navigate(R.id.nav_tutor_home)
        }
        return binding.root
    }

}