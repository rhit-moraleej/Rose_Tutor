package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.Student
import com.example.rosetutortracker.models.StudentHelpViewModel
import com.example.rosetutortracker.ui.TutorHomeFragment

class StudentHelpAdapter(fragment: TutorHomeFragment): BaseAdapter<Student>(fragment){

    override val model = ViewModelProvider(fragment.requireActivity())[StudentHelpViewModel::class.java]

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_student, parent, false)
    )


    inner class StudentViewHolder(itemView: View): BaseViewHolder<Student>(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.student_name)
        private val notifyCheckbox: CheckBox = itemView.findViewById(R.id.notify_student_checkbox)
        private val resolveButton: Button = itemView.findViewById(R.id.resolve_button)
        private val helpMessage: TextView = itemView.findViewById(R.id.help_message)

        override fun bind(item: Student) {
            studentName.text = item.name

        }

    }
}