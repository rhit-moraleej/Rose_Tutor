package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.ui.FindTutorListFragment
import com.google.android.material.snackbar.Snackbar

class TutorAdaptor(val fragment: FindTutorListFragment): RecyclerView.Adapter<TutorAdaptor.TutorViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity())[FindTutorViewModel:: class.java]

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

    fun clearTutors(){
        model.clearTutors()
        notifyDataSetChanged()
    }

    inner class TutorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tutorName: TextView = itemView.findViewById(R.id.tutor_name)
        private val tutorAvailability: ImageView = itemView.findViewById(R.id.tutor_availablity)
        private val notifyButton: Button = itemView.findViewById(R.id.notify_tutor)
        private val available = R.drawable.ic_baseline_check_24
        private val unavailable = R.drawable.ic_baseline_close_24

        fun bind(tutor: Tutor){
            tutorName.text = tutor.name
            if (tutor.available) {
                tutorAvailability.setImageResource(available)
            }else{
                tutorAvailability.setImageResource(unavailable)
            }
            // Need to reset alpha and clickable between searches
            notifyButton.alpha = 1F
            notifyButton.isClickable = true
            setupButtons()
        }

        private fun setupButtons(){
            notifyButton.setOnClickListener {
                model.updatePos(adapterPosition)
                if (!model.getCurrentTutor().available){
                    notifyButton.isClickable = false
                    return@setOnClickListener
                }
                val message = "Notifying ${model.getCurrentTutor().name} that you need help"
                Snackbar.make(itemView, message, Snackbar.LENGTH_SHORT)
                    .show()
                notifyButton.isClickable = false
                notifyButton.alpha = 0.5F
            }
        }
    }
}