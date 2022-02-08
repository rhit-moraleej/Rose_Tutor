package com.example.rosetutortracker.setup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.ActivityMainBinding
import com.example.rosetutortracker.databinding.FragmentUserDetailsBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.google.firebase.auth.FirebaseAuth

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var navBinding: ActivityMainBinding
    private lateinit var navProfile: ImageView
    private lateinit var navUserName: TextView
    private lateinit var navUserEmail: TextView
    private lateinit var studentModel: StudentViewModel
    private var name = ""
    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        navBinding = ActivityMainBinding.inflate(layoutInflater)
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        binding.cancelUserProfileButton.setOnClickListener {
            findNavController().popBackStack()
            FirebaseAuth.getInstance().signOut()
        }

        updateView()

        binding.updateUserProfileButton.setOnClickListener {
            Log.d("Student", "StudentModel before setup: ${studentModel.student}")
            val userclass: Int = binding.userClass.text.toString().toInt()
            val temp = studentModel.studentTemp
            val major = binding.userMajor.text.toString()
            Log.d("studentTemp", "$temp")
//            if (temp != null) {
//                Log.d("update", "${temp.name}, ${temp.email}, ${temp.major}")
//                studentModel.update(temp.name, temp.email, temp.major, userclass, true)
//            }
//            studentModel.update(userclass, true)
            studentModel.update(name, email, major, userclass, true)
//            updateNavHeader()
            findNavController().navigate(R.id.nav_home)
        }

        return binding.root
    }

    private fun updateView() {
        val temp = studentModel.studentTemp // for some reason temp loses its values
        Log.d("update", "temp while updating view: ${temp?.name}, ${temp?.email}")
        name = studentModel.studentTemp?.name!!
        email = studentModel.studentTemp?.email!!
        binding.userName.text = getString(R.string.name, studentModel.studentTemp?.name)
        binding.userEmail.text = getString(R.string.email_edit, studentModel.studentTemp?.email)
    }

//    private fun updateNavHeader() {
//        navUserName = navBinding.navView.getHeaderView(0).findViewById(R.id.user_name)
//        navUserEmail = navBinding.navView.getHeaderView(0).findViewById(R.id.user_email)
//        navUserName.text = studentModel.student?.name!!
//
//    }

}