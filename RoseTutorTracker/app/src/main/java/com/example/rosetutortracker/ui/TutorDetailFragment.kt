package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class TutorDetailFragment : Fragment() {
    private lateinit var binding: FragmentTutorDetailBinding
    private lateinit var model: FindTutorViewModel
    private lateinit var homeModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model =ViewModelProvider(requireActivity()).get(FindTutorViewModel:: class.java)
        homeModel = ViewModelProvider(requireActivity()).get(HomeViewModel:: class.java)
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView()
        setupButtons()
        return binding.root
    }

    private fun updateView() {
        val tutor: Tutor = model.getCurrentTutor()
        binding.tutorName.text = "Name: ${tutor.name}"
        binding.tutorEmail.text = "Email: ${tutor.email}"
        binding.tutorClass.text = "Class of ${tutor.classYear}"

    }

    private fun setupButtons(){
        binding.notifyTutor.setOnClickListener {
            if (!model.getCurrentTutor().available){
                binding.notifyTutor.isClickable = false
                return@setOnClickListener
            }
            val message = "Notifying ${model.getCurrentTutor().name} that you need help"
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Continue") {
                    findNavController().navigate(R.id.nav_message_tutor)
                }
                .show()
            //clicking button should move to message screen
            binding.notifyTutor.isClickable = false
            binding.notifyTutor.alpha = 0.5F
        }
       binding.favoriteTutor.setOnClickListener {
           val tutor = model.getCurrentTutor()
           homeModel.addTutor(tutor)
       }
    }
}