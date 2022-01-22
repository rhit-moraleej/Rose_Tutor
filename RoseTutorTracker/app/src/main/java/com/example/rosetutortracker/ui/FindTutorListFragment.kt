package com.example.rosetutortracker.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosetutortracker.R
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
            val random = Random.nextInt(20)
            for (i in 0..random){
                adaptor.addTutor(null)
            }
            hideKeyboard()
        }

        val choice = resources.getStringArray(R.array.search_spinner)
        val spinAdaptor = ArrayAdapter(this, android.R.layout.simple_spinner_item, choice)
        return binding.root
    }

    // Needed to close keyboard when search button is hit
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}