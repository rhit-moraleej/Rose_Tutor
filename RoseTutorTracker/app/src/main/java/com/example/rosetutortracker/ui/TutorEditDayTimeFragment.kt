package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentEditTutorDetailsBinding
import com.example.rosetutortracker.databinding.FragmentTutorEditDayTimeBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorEditDayTimeFragment: Fragment() {
    private lateinit var binding: FragmentTutorEditDayTimeBinding
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
            location= tutorModel.tutor?.location!!,
            hasCompletedSetup=tutorModel.tutor?.hasCompletedSetup!!,
            overRating = tutorModel.tutor?.overRating!!,
            numRatings = tutorModel.tutor?.numRatings!!,
            days = tutorModel.tutor?.days!!
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
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val monStartHour = startpicker.hour
                val monStartMinute = startpicker.minute
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                Log.d("rr",s)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val monEndHour = endpicker.hour
                    val monEndMinute = endpicker.minute
                    val t = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)
                    Log.d("rr",t)
                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")


        }

        binding.tuesdayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }

        binding.wednesdayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }

        binding.thursdayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }

        binding.fridayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }

        binding.saturdayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }

        binding.sundayButton.setOnClickListener {
            Log.d("rr","monday button")
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(parentFragmentManager,"rr")
            }
            startpicker.show(parentFragmentManager,"rr")
        }
        
        return binding.root
    }
}