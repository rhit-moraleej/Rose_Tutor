package com.example.rosetutortracker.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import rosefire.rosefire.Rosefire
import rosefire.rosefire.RosefireResult


class LoginFragment : Fragment() {

    private val RC_ROSEFIRE_LOGIN = 1001
    private val REGISTRY_TOKEN: String = "0cd2cc6d-7a3e-4af7-9b45-6d3d13a927d9"//R.string.rosefire_key

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: AuthStateListener? = null


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        val loginButton: View = binding.loginBtn

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            val username = user?.uid ?: "null"

            loginButton.visibility = if (user != null) View.GONE else View.VISIBLE

        }

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val result: RosefireResult = Rosefire.getSignInResultFromIntent(data)
            FirebaseAuth.getInstance().signInWithCustomToken(result.getToken())


        }
        loginButton.setOnClickListener {
            val signInIntent = Rosefire.getSignInIntent(requireContext(), REGISTRY_TOKEN.toString())
            Log.d("tag","login")
            resultLauncher.launch(signInIntent)
        }


        return binding.root


    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }


}