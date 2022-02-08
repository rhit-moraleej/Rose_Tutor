package com.example.rosetutortracker.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.TutorCourseAdaptor
import com.example.rosetutortracker.databinding.FragmentChangeCourseBinding
import com.example.rosetutortracker.models.TutorViewModel

open class ChangeCourseFragment : Fragment() {

    lateinit var binding: FragmentChangeCourseBinding
    lateinit var tutorModel: TutorViewModel
    lateinit var adapter: TutorCourseAdaptor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeCourseBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        adapter = TutorCourseAdaptor(this)
        adapter.setTutorCourses(tutorModel.tutor?.courses!!)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.setHasFixedSize(true)
//        updateView()
        binding.addCourse.setOnClickListener {
            adapter.addCourse()
//            updateView()
        }
//        binding.completeBtn.setOnClickListener {
//            tutorModel.updateTutorCourses(adapter.model.list)
//        }
        setupDoneButton()
        return binding.root
    }

    open fun setupDoneButton() {
        binding.completeBtn.setOnClickListener {
            tutorModel.updateTutorCourses(adapter.model.list)
        }
    }

    private fun updateView() {
        if (adapter.model.list.last().isBlank()) {
            binding.addCourse.isClickable = false
            binding.addCourse.alpha = 0.5f
        } else {
            binding.addCourse.isClickable = true
            binding.addCourse.alpha = 1.0f
        }
    }
}