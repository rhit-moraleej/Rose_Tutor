package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.databinding.FragmentMessageTutorBinding
import com.example.rosetutortracker.models.FindTutorViewModel
import com.example.rosetutortracker.models.StudentRequests
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.utils.KeyBoardUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageTutorFragment : Fragment() {
    private lateinit var binding: FragmentMessageTutorBinding
    private lateinit var model: FindTutorViewModel
    private lateinit var homeModel: StudentViewModel
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_MESSAGE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity())[FindTutorViewModel::class.java]
        binding = FragmentMessageTutorBinding.inflate(inflater, container, false)
        homeModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendButton.setOnClickListener {
            val useModel = if (model.size() == 0) homeModel else model
            Log.d("debug", "size of model: ${model.size()}")
            Log.d("debug", "size of studenTModel: ${homeModel.size()}")
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Send message?")
                .setMessage("Are you sure you want to send this message?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val message = StudentRequests(
                        binding.messageToTutor.text.toString(),
                        homeModel.tutorToSendMessage,
                        Firebase.auth.uid!!,
                        homeModel.student?.name!!,
                        useModel.getCurrent().studentInfo.name
                    )
                    Log.wtf("rr", message.message)
                    ref.add(message)
                    //Have the message actually sent
                    KeyBoardUtils.hideKeyboard(requireView(), requireActivity())
                    findNavController().popBackStack()
                    findNavController().popBackStack()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

        return binding.root
    }
}