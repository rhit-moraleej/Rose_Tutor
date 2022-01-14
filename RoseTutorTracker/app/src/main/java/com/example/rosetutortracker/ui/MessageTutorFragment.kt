package com.example.rosetutortracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentMessageTutorBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MessageTutorFragment: Fragment() {
    private lateinit var binding: FragmentMessageTutorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                }
                .setNegativeButton(android.R.string.cancel,null)
                .show()
        }

        return binding.root
    }


}