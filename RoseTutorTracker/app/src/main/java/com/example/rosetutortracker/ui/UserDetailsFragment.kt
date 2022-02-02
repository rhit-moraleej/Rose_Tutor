package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var ref: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        binding.cancelUserProfileButton.setOnClickListener {
            findNavController().popBackStack()
            FirebaseAuth.getInstance().signOut();
        }

        binding.updateUserProfileButton.setOnClickListener {
            val user = Firebase.auth.currentUser
            if (user != null) {
                Log.d("tag", "User: ${user.uid}, ${user.email}, ${user.displayName}")
                val userEmail = user.uid + "@rose-hulman.edu"
                ref = Firebase.firestore.collection("Students").document(user.uid)
                val username = binding.userName.text.toString()
                val userclass = binding.userClass.text.toString()
                ref.update("name",username)
                ref.update("classYear",userclass)

                findNavController().navigate(R.id.nav_home)
            }
        }

        return binding.root
    }

}