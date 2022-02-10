package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.FindTutorListFragment

class TutorAdaptor(fragment: FindTutorListFragment) : BaseAdapter<Tutor>(fragment) {
    override val model =
        ViewModelProvider(fragment.requireActivity())[FindTutorViewModel::class.java]

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = TutorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false),
        fragment,
        model
    )

    fun addTutor(tutor: Tutor?) {
        model.addTutor(tutor)
        notifyDataSetChanged()
    }

    fun findTutor(searchBy: Int, searchTerm: String) {
        model.findTutor(searchBy, searchTerm) {
            notifyDataSetChanged()
        }
    }

    fun clearTutors() {
        model.clearTutors()
        notifyDataSetChanged()
    }

}