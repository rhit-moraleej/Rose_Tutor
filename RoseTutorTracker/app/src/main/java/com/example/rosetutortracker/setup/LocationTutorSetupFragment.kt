package com.example.rosetutortracker.setup

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.edit.TutorEditLocationFragment
import com.example.rosetutortracker.models.Tutor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LocationTutorSetupFragment : TutorEditLocationFragment() {
    override fun setLocationButton() {
        binding.updateLocationButton.text = "Set Location"
        binding.updateLocationButton.setOnClickListener {
            updatedTutor.location = binding.locationEditText.text.toString()
            Log.d("location", "updated tutor: $updatedTutor")
//            ref.document(Firebase.auth.uid!!).set(updatedTutor)
            findNavController().navigate(R.id.nav_time_tutor_setup)
        }
    }

    override fun updateTutor() {
        updatedTutor = Tutor(
            courses = tutorModel.tutorTemp?.courses!!
        )
    }
}