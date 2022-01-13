package com.example.rosetutortracker.ui.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rosetutortracker.databinding.FragmentFindTutorListBinding

class FindTutorListFragment : Fragment() {
    private lateinit var binding: FragmentFindTutorListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindTutorListBinding.inflate(inflater, container, false)

        return binding.root
    }

}