package com.example.rosetutortracker.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.TutorCourseAdaptor
import com.example.rosetutortracker.databinding.FragmentChangeCourseBinding
import com.example.rosetutortracker.models.TutorViewModel
import com.example.rosetutortracker.utils.KeyBoardUtils

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
        Log.d("course1", tutorModel.tutor?.courses.toString())
        adapter = TutorCourseAdaptor(this)
        adapter.setTutorCourses(tutorModel.tutor?.courses!!)
        Log.d("course4", tutorModel.tutor?.courses.toString())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.setHasFixedSize(true)
        binding.addCourse.setOnClickListener {
            adapter.addCourse()
        }
        setupDoneButton()
        return binding.root
    }

    open fun setupDoneButton() {
        binding.completeBtn.setOnClickListener {
            tutorModel.updateTutorCourses(adapter.model.list)
            KeyBoardUtils.hideKeyboard(requireView(), requireActivity())
            findNavController().popBackStack()
        }
    }
}