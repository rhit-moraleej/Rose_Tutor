package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentEditTutorDetailsBinding
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorEditDetailFragment : Fragment() {
    private lateinit var binding: FragmentEditTutorDetailsBinding
    private lateinit var tutorModel: TutorViewModel
    private var ref = Firebase.firestore.collection("Tutors")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTutorDetailsBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]

        Log.d("rr", "tutor after getting: ${tutorModel.tutor}")
        updateView(tutorModel.tutor!!)
        binding.updateProfileButton.setOnClickListener {
            updateTutor(tutorModel.tutor!!)
        }
        return binding.root
    }

    private fun updateTutor(tutor: Tutor) {
        val updatedTutor = Tutor(
            available = tutor.available,
            courses = tutor.courses,
            overRating = tutor.overRating,
            numRatings = tutor.numRatings
        )
        Log.d("rr", "updating tutor to: $updatedTutor")
        ref.document(tutorModel.tutor!!.id).set(updatedTutor)
    }


    private fun updateView(tutor: Tutor){
//        binding.tutorName.setText(tutor.name)
//        binding.tutorEmail.setText(tutor.email)
//        binding.tutorClass.setText(tutor.classYear.toString())
        binding.courses.setText(tutor.coursesToString())
        binding.location.setText(tutor.location)
        binding.courses.isClickable = false
        binding.location.isClickable = false
        binding.tutorRating.text = getString(R.string.tutor_rating, tutor.overRating, tutor.numRatings)
    }

}