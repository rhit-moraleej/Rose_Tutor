package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentEditTutorDetailsBinding
import com.example.rosetutortracker.models.Student
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorEditDetailFragment : Fragment() {
    private lateinit var binding: FragmentEditTutorDetailsBinding
    private lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTutorDetailsBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        Log.d("rr", "tutor after getting: ${tutorModel.tutor}")
        updateView(tutorModel.tutor!!, studentModel.student!!)
        binding.updateProfileButton.setOnClickListener {
            updateTutor(tutorModel.tutor!!)
        }
        return binding.root
    }

    private fun updateTutor(tutor: Tutor) {
        val updatedTutor = Tutor(
            available = tutor.available,
            courses = tutor.courses,
            location= tutor.location,
            overRating = tutor.overRating,
            numRatings = tutor.numRatings
        )
        Log.d("rr", "updating tutor to: $updatedTutor")
        ref.document(Firebase.auth.uid!!).set(updatedTutor)
    }


    private fun updateView(tutor: Tutor, student: Student){
        binding.tutorName.setText(student.name)
        binding.tutorEmail.setText(student.email)
        binding.tutorClass.setText(student.classYear.toString())
        binding.courses.setText(tutor.coursesToString())
        binding.location.setText(tutor.location)
        binding.courses.isClickable = false
        binding.location.isClickable = false
    }

}