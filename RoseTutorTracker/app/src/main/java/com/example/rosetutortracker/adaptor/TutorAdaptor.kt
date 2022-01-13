package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.R
import com.example.rosetutortracker.ui.tutor.FindTutorListFragment
import com.example.rosetutortracker.ui.tutor.FindTutorViewModel
import com.example.rosetutortracker.ui.tutor.Tutor
import kotlin.random.Random

class TutorAdaptor(val fragment: FindTutorListFragment): RecyclerView.Adapter<TutorAdaptor.TutorViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(FindTutorViewModel:: class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_tutor, parent, false)
        return TutorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        holder.bind(model.getTutorAt(position))
    }

    override fun getItemCount() = model.size()

    inner class TutorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tutorName: TextView = itemView.findViewById(R.id.tutor_name)
        private val tutorAvailability: ImageView = itemView.findViewById(R.id.tutor_availablity)
        private val available = R.drawable.ic_baseline_check_24
        private val unavailable = R.drawable.ic_baseline_close_24

        fun bind(tutor: Tutor){
            tutorName.text = tutor.name
            if (Random.nextBoolean()) {
                tutorAvailability.setImageResource(available)
            }else
                tutorAvailability.setImageResource(unavailable)
        }
    }
}