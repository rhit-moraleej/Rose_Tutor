package com.example.rosetutortracker.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import rosefire.rosefire.Rosefire
import rosefire.rosefire.RosefireResult


class LoginFragment : Fragment() {

    private val RC_ROSEFIRE_LOGIN = 1001
    private val REGISTRY_TOKEN: String = "0cd2cc6d-7a3e-4af7-9b45-6d3d13a927d9"//R.string.rosefire_key

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: AuthStateListener? = null
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var ref: DocumentReference


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        val loginButton: View = binding.loginBtn
        val logoutButton: View = binding.logoutBtn

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            val username = user?.uid ?: "null"
            with(user) {
                if (user!=null) {
                    Log.d("tag", "User: ${this?.uid}, ${this?.email}, ${this?.displayName}")
                    ref = Firebase.firestore.collection("Students").document(username)
                    ref.get()
                        .addOnSuccessListener { document ->
                            val completedSetup: Boolean =
                                document.data?.get("hasCompletedSetup") as Boolean
                            if (completedSetup) {
                                findNavController().navigate(R.id.nav_home)
                            } else {
                                findNavController().navigate(R.id.nav_user_details)
                            }
                        }
                }
                else {
                    //findNavController().navigate(R.id.nav_user_details)



                }

            }
            loginButton.visibility = if (user != null) View.GONE else View.VISIBLE

        }

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val result: RosefireResult = Rosefire.getSignInResultFromIntent(data)
            FirebaseAuth.getInstance().signInWithCustomToken(result.getToken())
            //findNavController().navigate(R.id.nav_user_details)


        }
        loginButton.setOnClickListener {
            val signInIntent = Rosefire.getSignInIntent(requireContext(), REGISTRY_TOKEN.toString())
            Log.d("tag","login")
            resultLauncher.launch(signInIntent)
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
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