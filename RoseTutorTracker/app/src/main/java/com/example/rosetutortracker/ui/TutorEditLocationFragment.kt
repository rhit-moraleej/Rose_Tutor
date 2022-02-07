package com.example.rosetutortracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentEditTutorDetailsBinding
import com.example.rosetutortracker.databinding.FragmentTutorEditDayTimeBinding
import com.example.rosetutortracker.databinding.FragmentTutorEditLocationBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorEditLocationFragment : Fragment() {

    private lateinit var binding: FragmentTutorEditLocationBinding
    private lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTutorEditLocationBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        val updatedTutor = Tutor(
            available = tutorModel.tutor?.available!!,
            courses = tutorModel.tutor?.courses!!,
            location= tutorModel.tutor?.location!!,
            hasCompletedSetup=tutorModel.tutor?.hasCompletedSetup!!,
            overRating = tutorModel.tutor?.overRating!!,
            numRatings = tutorModel.tutor?.numRatings!!,
            days = tutorModel.tutor?.days!!,
            startHours = tutorModel.tutor?.startHours!!,
            startMinutes = tutorModel.tutor?.startMinutes!!,
            endHours = tutorModel.tutor?.endHours!!,
            endMinutes = tutorModel.tutor?.endMinutes!!
        )

        binding.locationEditText.setText(updatedTutor.location)

        binding.updateLocationButton.setOnClickListener {
            updatedTutor.location = binding.locationEditText.text.toString()
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }


        return binding.root
    }


}