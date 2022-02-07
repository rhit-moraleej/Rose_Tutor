package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.google.android.material.snackbar.Snackbar

open class TutorDetailFragment : Fragment() {
    lateinit var binding: FragmentTutorDetailBinding
    private lateinit var model: FindTutorViewModel
    lateinit var homeModel: StudentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity())[FindTutorViewModel:: class.java]
        homeModel = ViewModelProvider(requireActivity())[StudentViewModel:: class.java]
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView()
        setupNotifyButton()
        setupFavButton()
        return binding.root
    }

    open fun updateView() {
        Log.d(Constants.TAG, "FindTutor: ${model.list.size}, StudentModel: ${homeModel.list.size}")
        val tutor: Tutor = model.getCurrent()
        binding.tutorName.text = getString(R.string.place_holder_name, tutor.studentInfo.name)
        binding.tutorEmail.text = getString(R.string.placer_holder_email, tutor.studentInfo.email)
        binding.tutorClass.text = getString(R.string.place_holder_classyear, tutor.studentInfo.classYear)
        binding.courses.text = tutor.coursesToString()
        binding.location.text = getString(R.string.location, tutor.location)
        binding.tutorRating.text = getString(R.string.tutor_rating, tutor.overRating, tutor.numRatings)
        Log.d(Constants.TAG, "Students favs: ${homeModel.student?.favoriteTutors}")
        Log.d(Constants.TAG, "Current tutors id: ${tutor.id}")
        if(!homeModel.containsTutor(tutor))
            binding.favoriteTutor.text = getString(R.string.favorite_tutor)
        else{
            binding.favoriteTutor.text = getString(R.string.unfav)
        }
    }

    private fun setupNotifyButton(){
        binding.notifyTutor.setOnClickListener {
            if (!model.getCurrent().available){
                binding.notifyTutor.isClickable = false
                return@setOnClickListener
            }
            else {
                Log.d("rr","button clicked")
                val message = "Notifying ${binding.tutorName.text.substring(6)} that you need help"
                Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Continue") {
                        val tutor: Tutor = model.getCurrent()
                        homeModel.tutorToSendMessage = tutor.id
                        Log.d("rr",tutor.id)
                        findNavController().navigate(R.id.nav_message_tutor)
                    }
                    .show()
            }

            //clicking button should move to message screen
            binding.notifyTutor.isClickable = true
            binding.notifyTutor.alpha = 0.5F
        }
    }

    open fun setupFavButton(){
        binding.favoriteTutor.setOnClickListener {
            val tutor = model.getCurrent()
            Log.d(Constants.TAG, "Students favs: ${homeModel.student?.favoriteTutors}")
            if(!homeModel.containsTutor(tutor)){
                homeModel.addTutor(tutor)
            }else{
                homeModel.removeTutor(tutor)
            }
            updateView()
        }
    }
}