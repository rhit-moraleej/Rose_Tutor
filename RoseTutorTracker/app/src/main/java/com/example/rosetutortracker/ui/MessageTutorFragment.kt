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
    lateinit var homeModel: StudentViewModel
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
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Send message?")
                .setMessage("Are you sure you want to send this message?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val message = StudentRequests(
                        binding.messageToTutor.text.toString(),
                        homeModel.tutorToSendMessage,
                        Firebase.auth.uid!!,
                        homeModel.student?.name!!,
                        model.getCurrent().studentInfo.name
                    )
                    Log.wtf("rr", message.message)
                    ref.add(message)
                    //Have the message actually sent
                    KeyBoardUtils.hideKeyboard(requireView(), requireActivity())
                    findNavController().popBackStack()
                    findNavController().popBackStack()
//                    randomTutorNotification()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

        return binding.root
    }

//    private fun randomTutorNotification(){
//        val time = Random.nextInt()
//        val tutor = model.getCurrent()
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("${tutor.name} has resolved your problem")
//            .setMessage("${tutor.name} has marked your help request as solved, you you like to provide a rating?")
//            .setPositiveButton(android.R.string.ok){ _, _ ->
//                Log.d("rating", "user chose to rate ${tutor.name}")
////                rateTutor()
//            }
//            .setNegativeButton(android.R.string.cancel, null)
//            .show()
//    }

//    private fun rateTutor(){
//        val tutor = model.getCurrent()
//        val dialogLayout = requireActivity().layoutInflater.inflate(R.layout.tutor_rating_alert_dialod, null)
//        val editText = dialogLayout.findViewById<EditText>(R.id.tutor_rating_edittext)
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("Please rate ${tutor.name}")
//            .setMessage("On a scale from 1 to 5, please rate your experience with ${tutor.name}")
//            .setView(dialogLayout)
//            .setPositiveButton(android.R.string.ok){ _, _ ->
//
//            }
//            .setNegativeButton(android.R.string.cancel, null)
//            .show()
//    }
}