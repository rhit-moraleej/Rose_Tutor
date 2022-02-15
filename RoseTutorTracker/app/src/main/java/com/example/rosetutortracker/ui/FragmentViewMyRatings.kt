package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentViewMyRatingsBinding
import com.example.rosetutortracker.models.TutorViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentViewMyRatings : Fragment() {

    private lateinit var binding: FragmentViewMyRatingsBinding
    private lateinit var tutorModel: TutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewMyRatingsBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        val ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
        ref.document(Firebase.auth.uid!!).get()
            .addOnCompleteListener {
                Log.d("rate","${it.result!!.data!!["numRatings"]}")
                Log.d("rate","${it.result!!.data!!["overRating"]}")
                Log.d("rate",
                    ("%.1f".format(it.result!!.data!!["overRating"])).toFloat().toString()
                )
                binding.ratingsBar.rating = ("%.1f".format(it.result!!.data!!["overRating"])).toFloat()
                binding.numRatings.text = "Number of ratings: ${it.result!!.data!!["numRatings"]}"
            }

        return binding.root
    }

}