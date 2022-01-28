package com.example.rosetutortracker.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.HomeFragment
import com.example.rosetutortracker.models.HomeViewModel

class FavTutorAdaptor(val fragment: HomeFragment): RecyclerView.Adapter<FavTutorAdaptor.TutorViewHolder>() {

    val model = ViewModelProvider(fragment.requireActivity())[HomeViewModel :: class.java]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false)
        return TutorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        holder.bind(model.getTutorAt(position))
    }

    override fun getItemCount() = model.size()

    fun addTutor(tutor: Tutor?){
        model.addTutor(tutor)
        notifyDataSetChanged()
    }

    inner class TutorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tutorName: TextView = itemView.findViewById(R.id.tutor_name)
        private val tutorAvailability: ImageView = itemView.findViewById(R.id.tutor_availablity)
        private val available = R.drawable.ic_baseline_check_24
        private val unavailable = R.drawable.ic_baseline_close_24

        init {
            itemView.setOnClickListener {
                model.updatePos(adapterPosition)
                fragment.findNavController().navigate(R.id.nav_tutor_detail)
            }
        }

        fun bind(tutor: Tutor){
            Log.d("tag",model.getCurrentTutor().name)
            tutorName.text = tutor.name
            if (tutor.available) {
                tutorAvailability.setImageResource(available)
            }else{
                tutorAvailability.setImageResource(unavailable)
            }
        }
    }
}