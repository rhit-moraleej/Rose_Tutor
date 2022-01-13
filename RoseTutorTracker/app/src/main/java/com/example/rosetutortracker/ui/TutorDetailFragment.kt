package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel

class TutorDetailFragment : Fragment() {
    private lateinit var binding: FragmentTutorDetailBinding
    private lateinit var model: FindTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model =ViewModelProvider(this).get(FindTutorViewModel:: class.java)
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView()
        return binding.root
    }

    private fun updateView() {
        Log.d("rr", "$(model.size())")
        val tutor: Tutor = model.getCurrentTutor()
        binding.tutorName.text = tutor.name
        binding.tutorEmail.text = tutor.email
        binding.tutorClass.text = "$(tutor.classYear)"
    }
}