package com.example.rosetutortracker.ui

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        navBinding = ActivityMainBinding.inflate(layoutInflater)
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        binding.cancelUserProfileButton.setOnClickListener {
            findNavController().popBackStack()
            FirebaseAuth.getInstance().signOut();
        }

        binding.updateUserProfileButton.setOnClickListener {
            val username = binding.userName.text.toString()
            val userclass: Int = binding.userClass.text.toString().toInt()
            studentModel.update(username, userclass, true)

            updateNavHeader()
            findNavController().navigate(R.id.nav_home)
        }

        return binding.root
    }

    private fun updateNavHeader() {
        navUserName = navBinding.navView.getHeaderView(0).findViewById(R.id.user_name)
        navUserEmail = navBinding.navView.getHeaderView(0).findViewById(R.id.user_email)
        navUserName.text = studentModel.student?.name!!

    }

}