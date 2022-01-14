package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor

class TutorDetailFragment : Fragment() {
    private lateinit var binding: FragmentTutorDetailBinding
    private lateinit var model: FindTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model =ViewModelProvider(requireActivity()).get(FindTutorViewModel:: class.java)
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView()
        return binding.root
    }

    private fun updateView() {
        val tutor: Tutor = model.getCurrentTutor()
        binding.tutorName.text = "Name: ${tutor.name}"
        binding.tutorEmail.text = "Email: ${tutor.email}"
        binding.tutorClass.text = "Class of ${tutor.classYear}"
    }
}