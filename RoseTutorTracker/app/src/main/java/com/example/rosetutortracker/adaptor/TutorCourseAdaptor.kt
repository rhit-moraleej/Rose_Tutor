package com.example.rosetutortracker.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.abstracts.BaseViewHolder
import com.example.rosetutortracker.edit.ChangeCourseFragment
import com.example.rosetutortracker.models.CourseViewModel

class TutorCourseAdaptor(fragment: ChangeCourseFragment) : BaseAdapter<String>(fragment) {
    override val model = ViewModelProvider(fragment.requireActivity())[CourseViewModel::class.java]
    override fun setViewHolder(parent: ViewGroup, viewType: Int) = CourseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_course, parent, false),
        model
    )

    fun setTutorCourses(courses: ArrayList<String>) {
        val coursesCopy = ArrayList<String>()
        coursesCopy.addAll(courses)
        model.list.clear()
        model.list.addAll(coursesCopy)
        notifyDataSetChanged()
    }

    fun addCourse() {
        model.list.add("")
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(itemView: View, model: CourseViewModel) :
        BaseViewHolder<String>(itemView) {
        private val deleteIcon: ImageView = itemView.findViewById(R.id.delete_course)
        private val editText: EditText = itemView.findViewById(R.id.course_edit)
        private val saveButton: Button = itemView.findViewById(R.id.save_btn)
        private val close = R.drawable.ic_baseline_close_24

        init {
            deleteIcon.setOnClickListener {
                model.updatePos(adapterPosition)
                model.removeCurrent()
//                notifyDataSetChanged()
                notifyItemRemoved(adapterPosition)
            }
            saveButton.setOnClickListener {
                model.setListAt(adapterPosition, editText.text.toString())
                notifyItemChanged(adapterPosition)
            }
        }

        override fun bind(item: String) {
            deleteIcon.setImageResource(close)
            editText.setText(item)
        }
    }
}