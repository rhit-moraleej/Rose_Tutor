package com.example.rosetutortracker.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentTutorEditDayTimeBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class TutorEditDayTimeFragment : Fragment() {
    lateinit var binding: FragmentTutorEditDayTimeBinding
    lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel
    lateinit var updatedTutor: Tutor
    private var checkboxs = ArrayList<CheckBox>()
    private var buttons = ArrayList<Button>()
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorEditDayTimeBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        updateTutor()
        buttons.add(binding.mondayButton)
        buttons.add(binding.tuesdayButton)
        buttons.add(binding.wednesdayButton)
        buttons.add(binding.thursdayButton)
        buttons.add(binding.fridayButton)
        buttons.add(binding.saturdayButton)
        buttons.add(binding.sundayButton)
        checkboxs.add(binding.mondayCB)
        checkboxs.add(binding.tuesdayCB)
        checkboxs.add(binding.wednesdayCB)
        checkboxs.add(binding.thursdayCB)
        checkboxs.add(binding.fridayCB)
        checkboxs.add(binding.saturdayCB)
        checkboxs.add(binding.sundayCB)

        for (i in 0 until buttons.size) {
            buttons[i].isEnabled = updatedTutor.days[i].working
            updateButton(buttons[i], i)
            checkboxs[i].isChecked = updatedTutor.days[i].working
            checkboxs[i].setOnCheckedChangeListener { _, isChecked ->
                buttons[i].isEnabled = isChecked
                updatedTutor.days[i].working = isChecked
                Log.d("rr", "updating tutor to: $updatedTutor")
                ref.document(Firebase.auth.uid!!).collection(Constants.COLLECTION_BY_DAY)
            }
        }
        setupDoneButton()
        return binding.root
    }

    private fun updateButton(button: Button, index: Int) {
        button.setOnClickListener {
            Log.d("rr", "sunday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.days[index].startHour)
                    .setMinute(updatedTutor.days[index].startMin)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val startHour = startpicker.hour
                val startMinute = startpicker.minute
                updatedTutor.days[index].startHour = startHour
                updatedTutor.days[index].startMin = startMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(startHour)
                        .setMinute(startMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val endHour = endpicker.hour
                    val endMinute = endpicker.minute
                    updatedTutor.days[index].endHour = endHour
                    updatedTutor.days[index].endMin = endMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }
    }

    open fun updateTutor() {
        updatedTutor = Tutor(
            available = tutorModel.tutor?.available!!,
            courses = tutorModel.tutor?.courses!!,
            location = tutorModel.tutor?.location!!,
            hasCompletedSetup = tutorModel.tutor?.hasCompletedSetup!!,
            overRating = tutorModel.tutor?.overRating!!,
            numRatings = tutorModel.tutor?.numRatings!!,
            days = tutorModel.tutor?.days!!
        )
    }

    open fun setupDoneButton() {
        binding.completeBtn.isVisible = false
    }
}