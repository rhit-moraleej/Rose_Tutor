package com.example.rosetutortracker.ui.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.adaptor.TutorAdaptor
import com.example.rosetutortracker.databinding.FragmentFindTutorListBinding
import kotlin.random.Random

class FindTutorListFragment : Fragment() {
    private lateinit var binding: FragmentFindTutorListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindTutorListBinding.inflate(inflater, container, false)

        val adaptor = TutorAdaptor(this)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        binding.search.setOnClickListener {
            adaptor.clearTutors()
            val random = Random.nextInt(5)
            for (i in 0..random){
                adaptor.addTutor(null)
            }
        }

        return binding.root
    }

}