package com.example.rosetutortracker.ui

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.Tutor

class FavTutorDetailFragment: TutorDetailFragment() {
    override fun updateView() {
        Log.d(Constants.TAG, "FindTutor: ${homeModel.list.size}, StudentModel: ${homeModel.list.size}")
        val tutor: Tutor = homeModel.getCurrent()
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

    override fun setupFavButton(){
        binding.favoriteTutor.setOnClickListener {
            val tutor = homeModel.getCurrent()
            Log.d(Constants.TAG, "Students favs: ${homeModel.student?.favoriteTutors}")
            if(!homeModel.containsTutor(tutor)){
                homeModel.addTutor(tutor)
            }else{
                homeModel.removeTutor(tutor)
            }
            findNavController().navigate(R.id.nav_home)
        }
    }
}