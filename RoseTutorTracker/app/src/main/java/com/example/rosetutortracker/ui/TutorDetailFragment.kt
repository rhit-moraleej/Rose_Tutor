package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.HomeViewModel
import com.example.rosetutortracker.models.Tutor

class TutorDetailFragment : Fragment() {
    private lateinit var binding: FragmentTutorDetailBinding
    private lateinit var model: FindTutorViewModel
    private lateinit var homeModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity())[FindTutorViewModel:: class.java]
        homeModel = ViewModelProvider(requireActivity())[HomeViewModel:: class.java]
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView()
        setupButtons()
        return binding.root
    }

    private fun updateView() {
        val tutor: Tutor = model.getCurrent()
        binding.tutorName.text = getString(R.string.place_holder_name, tutor.studentInfo.name)
        binding.tutorEmail.text = getString(R.string.placer_holder_email, tutor.studentInfo.email)
        binding.tutorClass.text = getString(R.string.place_holder_classyear, tutor.studentInfo.classYear)
        binding.courses.text = tutor.coursesToString()
        binding.location.text = getString(R.string.location, tutor.location)
        binding.tutorRating.text = getString(R.string.tutor_rating, tutor.overRating, tutor.numRatings)
//        if(!tutor.isFavorite)
//            binding.favoriteTutor.text = getString(R.string.favorite_tutor)
//        else
            binding.favoriteTutor.text = getString(R.string.unfav)
    }

    private fun setupButtons(){
        binding.notifyTutor.setOnClickListener {
            if (!model.getCurrent().available){
                binding.notifyTutor.isClickable = false
                return@setOnClickListener
            }
//            val message = "Notifying ${model.getCurrent().name} that you need help"
//            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
//                .setAction("Continue") {
//                    findNavController().navigate(R.id.nav_message_tutor)
//                }
//                .show()
            //clicking button should move to message screen
            binding.notifyTutor.isClickable = false
            binding.notifyTutor.alpha = 0.5F
        }
       binding.favoriteTutor.setOnClickListener {
           val tutor = model.getCurrent()
           if(!homeModel.containsTutor(tutor)){
               homeModel.addTutor(tutor)
//               Log.d("tag",model.getCurrent().name)
//               model.getCurrent().isFavorite = true

           }

           else{
//               model.getCurrent().isFavorite = false
               homeModel.removeCurrent()
           }
           updateView()
       }
    }
}