package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentRatingBinding
import com.example.rosetutortracker.models.RatingTutorViewModel


class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRatingBinding
    private lateinit var model: RatingTutorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[RatingTutorViewModel::class.java]

        model.getTutorInfo(arguments?.getString("tutorid")!!) // get id from intent somehow
        updateView()
        setupButton()
        return binding.root
    }

    private fun setupButton() {
        binding.closeBtn.setOnClickListener {
//            findNavController().navigate(R.id.nav_home)
            findNavController().popBackStack()
        }
        binding.rateBtn.setOnClickListener {
            val text = binding.rating.text
            if (text.isBlank() || !text.isDigitsOnly() || text.toString().toInt()<0 || text.toString().toInt()>5
                ) {
                binding.ratingInstruction.text = getString(R.string.rating_error_inst)
            } else {
                val rating = binding.rating.text.toString().toDouble()
                model.addRating(rating)
                Log.d("debug", "rating btn pressed!!")
//                findNavController().navigate(R.id.nav_home)
                findNavController().popBackStack()
                Log.d("debug", "rating btn pressed222!!")
            }
        }
    }

    private fun updateView() {
        binding.tutorResolvedMessage.text = getString(R.string.resolve_message, (arguments?.getString("tutorName")!!))
    }

}