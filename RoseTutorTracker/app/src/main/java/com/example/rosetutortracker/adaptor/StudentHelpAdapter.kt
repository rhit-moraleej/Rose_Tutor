package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.Student
import com.example.rosetutortracker.models.StudentHelpViewModel
import com.example.rosetutortracker.ui.tutor.TutorHomeFragment

class StudentHelpAdapter(val fragment: TutorHomeFragment): RecyclerView.Adapter<StudentHelpAdapter.StudentViewHolder>() {

    val model = ViewModelProvider(fragment.requireActivity())[StudentHelpViewModel::class.java]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(model.getStudentAt(position))
    }

    override fun getItemCount(): Int {
        return model.size()
    }


    inner class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.student_name)
        private val notifyCheckbox: CheckBox = itemView.findViewById(R.id.notify_student_checkbox)
        private val resolveButton: Button = itemView.findViewById(R.id.resolve_button)
        private val helpMessage: TextView = itemView.findViewById(R.id.help_message)

        fun bind(student: Student) {
            studentName.text = student.name

        }

    }
}