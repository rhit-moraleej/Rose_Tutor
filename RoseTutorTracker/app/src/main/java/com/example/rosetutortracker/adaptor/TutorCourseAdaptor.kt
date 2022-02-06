package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.R
import com.example.rosetutortracker.models.CourseViewModel
import com.example.rosetutortracker.ui.ChangeCourseFragment

class TutorCourseAdaptor(fragment: ChangeCourseFragment): BaseAdapter<String>(fragment) {
    override val model = ViewModelProvider(fragment.requireActivity())[CourseViewModel::class.java]
    override fun setViewHolder(parent: ViewGroup, viewType: Int) = CourseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_course, parent, false),
        fragment,
        model
    )

    fun addCourse(){
        model.list.add("")
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(itemView: View, fragment: Fragment, model: CourseViewModel) : BaseViewHolder<String>(itemView){
        private val deleteIcon: ImageView = itemView.findViewById(R.id.delete_course)
        private val editText: EditText = itemView.findViewById(R.id.course_edit)
        private val close = R.drawable.ic_baseline_close_24

        init {
            deleteIcon.setOnClickListener {
                model.updatePos(adapterPosition)
                model.removeCurrent()
                notifyDataSetChanged()
            }
        }
        override fun bind(item: String) {
            deleteIcon.setImageResource(close)
            editText.setText(item)
        }

    }
}