package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.HomeFragment

class FavTutorAdaptor(fragment: HomeFragment): BaseAdapter<Tutor>(fragment){

    override val model = ViewModelProvider(fragment.requireActivity())[StudentViewModel :: class.java]

    fun addTutor(tutor: Tutor?){
        model.addTutor(tutor)
        notifyDataSetChanged()
    }

    fun getFavTutors(){
        model.setupFavs(){
            notifyDataSetChanged()
        }
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = TutorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false),
        fragment,
        model
    )

}