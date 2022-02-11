package com.example.rosetutortracker.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.abstracts.BaseViewHolder
import com.example.rosetutortracker.models.StudentHelpViewModel
import com.example.rosetutortracker.models.StudentRequests
import com.example.rosetutortracker.ui.TutorHomeFragment

class StudentHelpAdapter(fragment: TutorHomeFragment) : BaseAdapter<StudentRequests>(fragment) {

    override val model =
        ViewModelProvider(fragment.requireActivity())[StudentHelpViewModel::class.java]

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_student, parent, false)
    )

    fun addListener(fragmentName: String){
        model.addListener(fragmentName){
            notifyDataSetChanged()
        }
    }
    fun removeListener(fragmentName: String){
        model.removeListener(fragmentName)
    }

    inner class StudentViewHolder(itemView: View) : BaseViewHolder<StudentRequests>(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.student_name)
        private val notifyCheckbox: CheckBox = itemView.findViewById(R.id.notify_student_checkbox)
        private val resolveButton: Button = itemView.findViewById(R.id.resolve_button)
        private val helpMessage: TextView = itemView.findViewById(R.id.help_message)

        init {
            notifyCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    notifyCheckbox.isEnabled = false
                    Log.d("notify","Notifying $studentName")
                }
            }

            resolveButton.setOnClickListener {
                model.updatePos(adapterPosition)
                model.resolveCurrentStudent()
                notifyItemRemoved(adapterPosition)
            }

        }

        override fun bind(item: StudentRequests) {
            studentName.text = if(item.senderName.length<15) item.senderName else item.senderName.substring(0,16)
            helpMessage.text = item.message
        }

    }
}