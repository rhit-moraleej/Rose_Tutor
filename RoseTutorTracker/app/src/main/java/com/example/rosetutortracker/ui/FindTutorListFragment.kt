package com.example.rosetutortracker.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.R
import com.example.rosetutortracker.adaptor.TutorAdaptor
import com.example.rosetutortracker.databinding.FragmentFindTutorListBinding
import kotlin.random.Random

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
        adaptor.addListener(fragmentName)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        binding.search.setOnClickListener {
            adaptor.clearTutors()
            val random = Random.nextInt(20)
//            for (i in 0..random){
//                adaptor.addTutor(null)
//            }
            val searchTerm = binding.tutorSearch.text.toString()
            Log.d("rr", "search term: $searchTerm")
            adaptor.findTutor(searchBy, searchTerm)
            hideKeyboard()
        }
        setupSpinner()
        return binding.root
    }

    private fun setupSpinner() {
        val choice = resources.getStringArray(R.array.search_spinner)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, choice)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    // Needed to close keyboard when search button is hit
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adaptor.removeLListener(fragmentName)
    }

    companion object{
        const val fragmentName: String = "FindTutorListFragment"
    }
}