package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.TutorCourseAdaptor
import com.example.rosetutortracker.databinding.FragmentChangeCourseBinding

class ChangeCourseFragment : Fragment() {

    private lateinit var binding: FragmentChangeCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeCourseBinding.inflate(inflater, container, false)
        var adapter = TutorCourseAdaptor(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.setHasFixedSize(true)
        binding.addCourse.setOnClickListener {
            adapter.addCourse()
        }
        return binding.root
    }
}