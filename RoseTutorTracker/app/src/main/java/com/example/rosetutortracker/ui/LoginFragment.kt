package com.example.rosetutortracker.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentLoginBinding
import com.example.rosetutortracker.models.Student
import com.example.rosetutortracker.models.StudentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import rosefire.rosefire.Rosefire
import rosefire.rosefire.RosefireResult


class LoginFragment : Fragment() {

    private val REGISTRY_TOKEN: String = "0cd2cc6d-7a3e-4af7-9b45-6d3d13a927d9"//R.string.rosefire_key

    private lateinit var authStateListener: AuthStateListener
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val loginButton: View = binding.loginBtn
        val logoutButton: View = binding.logoutBtn

        authStateListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            val username = user?.uid ?: "null"
            if (user != null) {
                val studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
                studentModel.getOrMakeUser {
                    if (studentModel.hasCompletedSetup()) {
                        findNavController().navigate(R.id.nav_home)
                    } else {
                        studentModel.student = Student(email = "$username@rose-hulman.edu")
                        findNavController().navigate(R.id.nav_user_details)
                    }
                }
            }
            loginButton.visibility = if (user != null) View.GONE else View.VISIBLE
        }

        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                val result: RosefireResult = Rosefire.getSignInResultFromIntent(data)
                FirebaseAuth.getInstance().signInWithCustomToken(result.token)
            }
        loginButton.setOnClickListener {
            val signInIntent = Rosefire.getSignInIntent(requireContext(), REGISTRY_TOKEN)
            Log.d("tag", "login")
            resultLauncher.launch(signInIntent)
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(authStateListener)
    }
}