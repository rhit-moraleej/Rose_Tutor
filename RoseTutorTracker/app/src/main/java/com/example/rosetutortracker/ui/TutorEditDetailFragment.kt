package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentEditTutorDetailsBinding
import com.example.rosetutortracker.models.Tutor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorEditDetailFragment : Fragment() {
    private lateinit var binding: FragmentEditTutorDetailsBinding
    private var tutorID: String = "nEhLSsbiLyHMV4wvpeds" // For testing purposes
    private var ref = Firebase.firestore.collection("Tutors")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTutorDetailsBinding.inflate(inflater, container, false)
        val tutor = getTutorDetails(tutorID)
        Log.d("rr", "tutor after getting: $tutor")
        binding.updateProfileButton.setOnClickListener {
            updateTutor(tutor)
        }
        return binding.root
    }

    private fun updateTutor(tutor: Tutor) {
        val updatedTutor = Tutor(
            binding.tutorName.text.toString(),
            binding.tutorEmail.text.toString(),
            binding.tutorClass.text.toString().toInt(),
            tutor.available,
            tutor.courses,
            tutor.isFavorite,
            tutor.overRating,
            tutor.numRatings
        )
        Log.d("rr", "updating tutor to: $updatedTutor")
        ref.document(tutorID).set(updatedTutor)
    }

    private fun getTutorDetails(ID: String): Tutor{
        var tutor = Tutor()
        ref.document(ID).get()
            .addOnCompleteListener { result ->
//                result.result!!
                tutor = Tutor.from(result.result!!)
                Log.d("rr", "$tutor")
                updateView(tutor)
            }
        return tutor
    }

    private fun updateView(tutor: Tutor){
        binding.tutorName.setText(tutor.name)
        binding.tutorEmail.setText(tutor.email)
        binding.tutorClass.setText(tutor.classYear.toString())
        binding.courses.isClickable = false
        binding.location.isClickable = false
        binding.tutorRating.text = getString(R.string.tutor_rating, tutor.overRating, tutor.numRatings)
    }

}