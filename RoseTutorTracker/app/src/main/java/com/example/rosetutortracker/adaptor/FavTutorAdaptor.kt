package com.example.rosetutortracker.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.HomeFragment
import java.util.*

class FavTutorAdaptor(fragment: HomeFragment) : BaseAdapter<Tutor>(fragment) {
    override val model = ViewModelProvider(fragment.requireActivity())[StudentViewModel::class.java]

    fun getFavTutors(): ArrayList<Tutor> {
        return model.setupFavs {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return setViewHolder(parent, viewType)
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false),
        fragment as HomeFragment,
        model
    )

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Log.d("FAVDEBUG", "list before move: ${model.student?.favoriteTutors}")
        Collections.swap(model.list, fromPosition, toPosition)
        Collections.swap(model.student?.favoriteTutors!!, fromPosition, toPosition)
        Log.d("FAVDEBUG", "list after move: ${model.student?.favoriteTutors}")
        model.updateFavs()
    }

    fun removeTutor(adapterPosition: Int) {
        model.updatePos(adapterPosition)
        model.removeTutor(model.getCurrent())
        model.updateFavs()
    }

    fun getDetails(adapterPosition: Int) {
        model.updatePos(adapterPosition)
        fragment.findNavController().navigate(R.id.nav_fav_tutor_detail)
    }


    inner class ItemViewHolder(itemView: View, fragment: HomeFragment, model: StudentViewModel) : TutorViewHolder(itemView, fragment, model) {
        init {
            itemView.setOnClickListener {
                Log.d("FAVDEBUG","Total number of tutors in favs: ${model.size()}")
                model.updatePos(adapterPosition)
                fragment.findNavController().navigate(R.id.nav_fav_tutor_detail)
            }
        }
    }
}
