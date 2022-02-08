package com.example.rosetutortracker.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorEditDayTimeBinding.inflate(inflater, container, false)
        tutorModel = ViewModelProvider(requireActivity())[TutorViewModel::class.java]
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        val updatedTutor = Tutor(
            available = tutorModel.tutor?.available!!,
            courses = tutorModel.tutor?.courses!!,
            location = tutorModel.tutor?.location!!,
            hasCompletedSetup = tutorModel.tutor?.hasCompletedSetup!!,
            overRating = tutorModel.tutor?.overRating!!,
            numRatings = tutorModel.tutor?.numRatings!!,
            days = tutorModel.tutor?.days!!,
            startHours = tutorModel.tutor?.startHours!!,
            startMinutes = tutorModel.tutor?.startMinutes!!,
            endHours = tutorModel.tutor?.endHours!!,
            endMinutes = tutorModel.tutor?.endMinutes!!
        )

        binding.mondayButton.isEnabled = updatedTutor.days[0]
        binding.tuesdayButton.isEnabled = updatedTutor.days[1]
        binding.wednesdayButton.isEnabled = updatedTutor.days[2]
        binding.thursdayButton.isEnabled = updatedTutor.days[3]
        binding.fridayButton.isEnabled = updatedTutor.days[4]
        binding.saturdayButton.isEnabled = updatedTutor.days[5]
        binding.sundayButton.isEnabled = updatedTutor.days[6]
        binding.mondayCB.isChecked = updatedTutor.days[0]
        binding.tuesdayCB.isChecked = updatedTutor.days[1]
        binding.wednesdayCB.isChecked = updatedTutor.days[2]
        binding.thursdayCB.isChecked = updatedTutor.days[3]
        binding.fridayCB.isChecked = updatedTutor.days[4]
        binding.saturdayCB.isChecked = updatedTutor.days[5]
        binding.sundayCB.isChecked = updatedTutor.days[6]


        binding.mondayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.mondayButton.isEnabled = isChecked
            updatedTutor.days[0] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.tuesdayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.tuesdayButton.isEnabled = isChecked
            updatedTutor.days[1] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.wednesdayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.wednesdayButton.isEnabled = isChecked
            updatedTutor.days[2] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.thursdayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.thursdayButton.isEnabled = isChecked
            updatedTutor.days[3] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.fridayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.fridayButton.isEnabled = isChecked
            updatedTutor.days[4] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.saturdayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.saturdayButton.isEnabled = isChecked
            updatedTutor.days[5] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }
        binding.sundayCB.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.sundayButton.isEnabled = isChecked
            updatedTutor.days[6] = isChecked
            Log.d("rr", "updating tutor to: $updatedTutor")
            ref.document(Firebase.auth.uid!!).set(updatedTutor)
        }

        binding.mondayButton.setOnClickListener {
            Log.d("rr", "monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[0])
                    .setMinute(updatedTutor.startMinutes[0])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val monStartHour = startpicker.hour
                val monStartMinute = startpicker.minute
                updatedTutor.startHours[0] = monStartHour
                updatedTutor.startMinutes[0] = monStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(monStartHour)
                        .setMinute(monStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val monEndHour = endpicker.hour
                    val monEndMinute = endpicker.minute
                    updatedTutor.endHours[0] = monEndHour
                    updatedTutor.endMinutes[0] = monEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")


        }

        binding.tuesdayButton.setOnClickListener {
            Log.d("rr", "tuesday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[1])
                    .setMinute(updatedTutor.startMinutes[1])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val tueStartHour = startpicker.hour
                val tueStartMinute = startpicker.minute
                updatedTutor.startHours[1] = tueStartHour
                updatedTutor.startMinutes[1] = tueStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(tueStartHour)
                        .setMinute(tueStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val tueEndHour = endpicker.hour
                    val tueEndMinute = endpicker.minute
                    updatedTutor.endHours[1] = tueEndHour
                    updatedTutor.endMinutes[1] = tueEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }

        binding.wednesdayButton.setOnClickListener {
            Log.d("rr", "wednesday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[2])
                    .setMinute(updatedTutor.startMinutes[2])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val wedStartHour = startpicker.hour
                val wedStartMinute = startpicker.minute
                updatedTutor.startHours[2] = wedStartHour
                updatedTutor.startMinutes[2] = wedStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(wedStartHour)
                        .setMinute(wedStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val wedEndHour = endpicker.hour
                    val wedEndMinute = endpicker.minute
                    updatedTutor.endHours[2] = wedEndHour
                    updatedTutor.endMinutes[2] = wedEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }

        binding.thursdayButton.setOnClickListener {
            Log.d("rr", "thursday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[3])
                    .setMinute(updatedTutor.startMinutes[3])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val thuStartHour = startpicker.hour
                val thuStartMinute = startpicker.minute
                updatedTutor.startHours[3] = thuStartHour
                updatedTutor.startMinutes[3] = thuStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(thuStartHour)
                        .setMinute(thuStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val thuEndHour = endpicker.hour
                    val thuEndMinute = endpicker.minute
                    updatedTutor.endHours[3] = thuEndHour
                    updatedTutor.endMinutes[3] = thuEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }

        binding.fridayButton.setOnClickListener {
            Log.d("rr", "friday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[4])
                    .setMinute(updatedTutor.startMinutes[4])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val friStartHour = startpicker.hour
                val friStartMinute = startpicker.minute
                updatedTutor.startHours[4] = friStartHour
                updatedTutor.startMinutes[4] = friStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(friStartHour)
                        .setMinute(friStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val friEndHour = endpicker.hour
                    val friEndMinute = endpicker.minute
                    updatedTutor.endHours[4] = friEndHour
                    updatedTutor.endMinutes[4] = friEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }

        binding.saturdayButton.setOnClickListener {
            Log.d("rr", "saturday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[5])
                    .setMinute(updatedTutor.startMinutes[5])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val satStartHour = startpicker.hour
                val satStartMinute = startpicker.minute
                updatedTutor.startHours[5] = satStartHour
                updatedTutor.startMinutes[5] = satStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(satStartHour)
                        .setMinute(satStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val satEndHour = endpicker.hour
                    val satEndMinute = endpicker.minute
                    updatedTutor.endHours[5] = satEndHour
                    updatedTutor.endMinutes[5] = satEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }

        binding.sundayButton.setOnClickListener {
            Log.d("rr", "sunday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(updatedTutor.startHours[6])
                    .setMinute(updatedTutor.startMinutes[6])
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val sunStartHour = startpicker.hour
                val sunStartMinute = startpicker.minute
                updatedTutor.startHours[6] = sunStartHour
                updatedTutor.startMinutes[6] = sunStartMinute
                val s = String.format("Time: %d:%02d", startpicker.hour, startpicker.minute)
                Log.d("rr", s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(sunStartHour)
                        .setMinute(sunStartMinute)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val sunEndHour = endpicker.hour
                    val sunEndMinute = endpicker.minute
                    updatedTutor.endHours[5] = sunEndHour
                    updatedTutor.endMinutes[5] = sunEndMinute
                    ref.document(Firebase.auth.uid!!).set(updatedTutor)
                    val t = String.format("Time: %d:%02d", endpicker.hour, endpicker.minute)
                    Log.d("rr", t)
                }
                endpicker.show(parentFragmentManager, "rr")
            }
            startpicker.show(parentFragmentManager, "rr")
        }
        setupDoneButton()
        return binding.root
    }

    open fun setupDoneButton() {
        binding.completeBtn.isVisible = false
    }
}