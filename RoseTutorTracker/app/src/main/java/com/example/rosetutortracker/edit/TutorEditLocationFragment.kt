package com.example.rosetutortracker.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentTutorEditLocationBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class TutorEditLocationFragment : Fragment() {

    lateinit var binding: FragmentTutorEditLocationBinding
    lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel
    lateinit var updatedTutor: Tutor
    var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTutorEditLocationBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        updateTutor()
        Log.d("location", "current tutor: ${tutorModel.tutor}")
        tutorModel.tutor = updatedTutor
        binding.locationEditText.setText(updatedTutor.location)
        setLocationButton()
        return binding.root
    }

    open fun setLocationButton() {
        binding.updateLocationButton.setOnClickListener {
            updatedTutor.location = binding.locationEditText.text.toString()
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
    }

    open fun updateTutor(){
        updatedTutor = Tutor(
            available = tutorModel.tutor?.available!!,
            courses = tutorModel.tutor?.courses!!,
            location = tutorModel.tutor?.location!!,
            hasCompletedSetup = tutorModel.tutor?.hasCompletedSetup!!,
            overRating = tutorModel.tutor?.overRating!!,
            numRatings = tutorModel.tutor?.numRatings!!,
            days = tutorModel.tutor?.days!!
        )
    }
}