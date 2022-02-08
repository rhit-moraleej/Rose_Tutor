package com.example.rosetutortracker.adaptor

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.BaseViewModel
import com.example.rosetutortracker.models.Tutor

open class TutorViewHolder(itemView: View, fragment: Fragment, model: BaseViewModel<Tutor>) :
    BaseViewHolder<Tutor>(itemView) {
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

    override fun bind(item: Tutor) {
        tutorName.text = item.studentInfo.name
        if (item.available) {
            tutorAvailability.setImageResource(available)
        } else {
            tutorAvailability.setImageResource(unavailable)
        }
    }

}