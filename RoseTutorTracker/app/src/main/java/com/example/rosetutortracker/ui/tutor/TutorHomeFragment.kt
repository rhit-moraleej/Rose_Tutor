package com.example.rosetutortracker.ui.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.FavTutorAdaptor
import com.example.rosetutortracker.adaptor.StudentHelpAdapter
import com.example.rosetutortracker.databinding.FragmentHomeBinding
import com.example.rosetutortracker.databinding.FragmentTutorHomeBinding
import android.R

import com.google.android.material.navigation.NavigationView




class TutorHomeFragment: Fragment() {
    private lateinit var binding: FragmentTutorHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorHomeBinding.inflate(inflater, container, false)

        val adapter = StudentHelpAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }
}