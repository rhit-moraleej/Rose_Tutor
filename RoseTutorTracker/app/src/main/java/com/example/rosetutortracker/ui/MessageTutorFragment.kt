package com.example.rosetutortracker.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentMessageTutorBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class MessageTutorFragment: Fragment() {
    private lateinit var binding: FragmentMessageTutorBinding
    private lateinit var model: FindTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity())[FindTutorViewModel:: class.java]
        binding = FragmentMessageTutorBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Send message?")
                .setMessage("Are you sure you want to send this message?")
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    //Have the message actually sent
                    findNavController().popBackStack()
                    findNavController().popBackStack()
                    randomTutorNotification()
                }
                .setNegativeButton(android.R.string.cancel,null)
                .show()
        }

        return binding.root
    }

    private fun randomTutorNotification(){
        val time = Random.nextInt()
        val tutor = model.getCurrentTutor()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("${tutor.name} has resolved your problem")
            .setMessage("${tutor.name} has marked your help request as solved, you you like to provide a rating?")
            .setPositiveButton(android.R.string.ok){ _, _ ->
                Log.d("rating", "user chose to rate ${tutor.name}")
                rateTutor()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun rateTutor(){
        val tutor = model.getCurrentTutor()
        val dialogLayout = requireActivity().layoutInflater.inflate(R.layout.tutor_rating_alert_dialod, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.tutor_rating_edittext)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Please rate ${tutor.name}")
            .setMessage("On a scale from 1 to 5, please rate your experience with ${tutor.name}")
            .setView(dialogLayout)
            .setPositiveButton(android.R.string.ok){ _, _ ->

            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}