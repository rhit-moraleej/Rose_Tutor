package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.StudentHelpAdapter
import com.example.rosetutortracker.databinding.FragmentTutorHomeBinding
import com.example.rosetutortracker.utils.NotificationUtils


class TutorHomeFragment : Fragment() {
    private lateinit var binding: FragmentTutorHomeBinding
    private lateinit var adaptor: StudentHelpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorHomeBinding.inflate(inflater, container, false)

        adaptor = StudentHelpAdapter(this)
//        adaptor.getMessages()
        adaptor.model.firstTime = true
        adaptor.addListener(fragmentName,requireContext())
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        NotificationUtils.createChannel(requireContext())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        adaptor.removeListener(fragmentName)
    }

    companion object {
        const val fragmentName: String = "TutorHomeFragment"
    }
}