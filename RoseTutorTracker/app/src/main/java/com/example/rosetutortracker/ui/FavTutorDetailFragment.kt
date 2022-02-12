package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseViewModel
import com.example.rosetutortracker.databinding.FragmentTutorDetailBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor

class FavTutorDetailFragment : TutorDetailFragment() {
    //    override var usedModel: BaseViewModel<Tutor> = homeModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity())[FindTutorViewModel::class.java]
        homeModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        binding = FragmentTutorDetailBinding.inflate(inflater, container, false)
        updateView(homeModel)
        setupNotifyButton(homeModel)
        setupFavButton(homeModel)
        return binding.root
    }

    override fun setupFavButton(usedModel: BaseViewModel<Tutor>) {
        binding.favoriteTutor.setOnClickListener {
            val tutor = usedModel.getCurrent()
            Log.d(Constants.TAG, "Students favs: ${homeModel.student?.favoriteTutors}")
            if (!homeModel.containsTutor(tutor)) {
                homeModel.addTutor(tutor)
            } else {
                homeModel.removeTutor(tutor)
            }
            findNavController().navigate(R.id.nav_home)
        }
    }
}