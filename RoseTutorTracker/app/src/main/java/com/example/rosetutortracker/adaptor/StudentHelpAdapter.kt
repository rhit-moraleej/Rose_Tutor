package com.example.rosetutortracker.adaptor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.abstracts.BaseAdapter
import com.example.rosetutortracker.abstracts.BaseViewHolder
import com.example.rosetutortracker.models.StudentHelpViewModel
import com.example.rosetutortracker.models.StudentRequests
import com.example.rosetutortracker.ui.TutorHomeFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentHelpAdapter(fragment: TutorHomeFragment) : BaseAdapter<StudentRequests>(fragment) {



    override val model =
        ViewModelProvider(fragment.requireActivity())[StudentHelpViewModel::class.java]

    override fun setViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_student, parent, false)
    )

    fun addListener(fragmentName: String, requireContext: Context) {
        model.addListener(fragmentName, requireContext) {
            notifyDataSetChanged()
        }
    }

    fun removeListener(fragmentName: String) {
        model.removeListener(fragmentName)
    }

    inner class StudentViewHolder(itemView: View) : BaseViewHolder<StudentRequests>(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.student_name)
        private val notifyCheckbox: CheckBox = itemView.findViewById(R.id.notify_student_checkbox)
        private val resolveButton: Button = itemView.findViewById(R.id.resolve_button)
        private val helpMessage: TextView = itemView.findViewById(R.id.help_message)

        val ref = Firebase.firestore.collection(Constants.COLLECTION_BY_MESSAGE)

        init {

            Log.d("checked","checked $itemCount")
            notifyCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    model.list[adapterPosition].notified = true
                    ref.document(model.list[adapterPosition].id).update("notified",true)
                    notifyCheckbox.isEnabled = false
                    Log.d("notify", "Notifying $studentName")
                    val refNotification =
                        Firebase.firestore.collection(Constants.COLLECTION_BY_NOTIFICATIONS)
                    val notifToAdd = hashMapOf(
                        "sender" to Firebase.auth.uid,
                        "receiver" to model.list[adapterPosition].sender,
                        "receiverName" to model.list[adapterPosition].receiverName
                    )
                    refNotification.add(notifToAdd)
                }
            }

            resolveButton.setOnClickListener {
                val posTemp = adapterPosition
                model.updatePos(adapterPosition)
                Log.d("cc",adapterPosition.toString())
                model.resolveCurrentStudent()

                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(posTemp,itemCount)
                Log.d("cc",adapterPosition.toString())
            }

        }

        override fun bind(item: StudentRequests) {
            Log.d("bind","checked $itemCount")
            studentName.text =
                if (item.senderName.length < 15) item.senderName else item.senderName.substring(
                    0,
                    16
                )
            helpMessage.text = item.message
            if (item.notified) {
                notifyCheckbox.isChecked = true
                notifyCheckbox.isEnabled = false
            }
            else {
                notifyCheckbox.isChecked = false
                notifyCheckbox.isEnabled = true
            }
        }

        override fun bindView(item: StudentRequests) {
            TODO("Not yet implemented")
        }

    }
}