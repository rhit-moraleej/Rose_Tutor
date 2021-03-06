package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.R
import com.example.rosetutortracker.adaptor.TutorAdaptor
import com.example.rosetutortracker.databinding.FragmentFindTutorListBinding
import com.example.rosetutortracker.utils.KeyBoardUtils

class FindTutorListFragment : Fragment() {
    private lateinit var binding: FragmentFindTutorListBinding
    private lateinit var adaptor: TutorAdaptor
    private var searchBy: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindTutorListBinding.inflate(inflater, container, false)
        adaptor = TutorAdaptor(this)
        adaptor.model.list.clear()
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        //make the search view show options while typing
        setupSearchView()
        setupSpinner()
        return binding.root
    }

    private fun setupSearchView() {
        binding.tutorSearch.isSubmitButtonEnabled = true
        binding.tutorSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    adaptor.clearTutors()
                    adaptor.findTutor(searchBy, query)
                    KeyBoardUtils.hideKeyboard(view!!, activity!!)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupSpinner() {
        val choice = resources.getStringArray(R.array.search_spinner)
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, choice)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                searchBy = position
                Log.d("rr", "searchBy index: $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}