package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.abstracts.BaseViewModel
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.HomeFragment

class FavTutorAdaptor(fragment: HomeFragment) : BaseAdapter<Tutor>(fragment) {

    override val model = ViewModelProvider(fragment.requireActivity())[StudentViewModel::class.java]

    fun addTutor(tutor: Tutor?) {
        model.addTutor(tutor)
        notifyDataSetChanged()
    }

    fun getFavTutors() {
        model.setupFavs {
            notifyDataSetChanged()
        }
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = FavTutorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false),
        fragment,
        model
    )

    inner class FavTutorViewHolder(
        itemView: View,
        fragment: Fragment,
        model: BaseViewModel<Tutor>
    ) : TutorViewHolder(itemView, fragment, model) {
        init {
            itemView.setOnClickListener {
                model.updatePos(adapterPosition)
                fragment.findNavController().navigate(R.id.nav_fav_tutor_detail)
            }
        }
    }

}