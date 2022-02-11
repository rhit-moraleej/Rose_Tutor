package com.example.rosetutortracker.setup

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rosetutortracker.BuildConfig
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.ActivityMainBinding
import com.example.rosetutortracker.databinding.FragmentUserDetailsBinding
import com.example.rosetutortracker.models.StudentViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var navBinding: ActivityMainBinding
    private lateinit var navProfile: ImageView
    private lateinit var navUserName: TextView
    private lateinit var navUserEmail: TextView
    private lateinit var studentModel: StudentViewModel
    private var name = ""
    private var email = ""

    private var latestTmpUri: Uri? = null

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    binding.userProfile.setImageURI(uri)
                    addPhotoFromUri(uri)
                }
            }
        }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.userProfile.setImageURI(uri)
                addPhotoFromUri(uri)
            }
        }

    private val storageImagesRef = Firebase.storage
        .reference
        .child("images")
    private var storageUriStringInFragment: String = ""

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
            studentModel.update(name, email, major, userclass, true, storageUriStringInFragment)
            Log.d("image",storageUriStringInFragment)
//            updateNavHeader()
            findNavController().navigate(R.id.nav_home)
        }

        binding.userEditUploadPhotoButton.setOnClickListener {
            showPictureDialog()
        }

        return binding.root
    }

    private fun updateView() {
        val temp = studentModel.studentTemp // for some reason temp loses its values
        //Log.d("update", "temp while updating view: ${temp?.name}, ${temp?.email}")
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

    private fun showPictureDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose a photo source")
        builder.setMessage("Would you like to take a new picture?\nOr choose an existing one?")
        builder.setPositiveButton("Take Picture") { _, _ ->
            binding.updateUserProfileButton.isEnabled = false
            binding.updateUserProfileButton.text = "Loading image"
            takeImage()
        }

        builder.setNegativeButton("Choose Picture") { _, _ ->
            binding.updateUserProfileButton.isEnabled = false
            binding.updateUserProfileButton.text = "Loading image"
            selectImageFromGallery()
        }
        builder.create().show()
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val tmpFile = File.createTempFile("JPEG_${timeStamp}_", ".png", storageDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun addPhotoFromUri(uri: Uri?) {
        if (uri == null) {
            Log.e("image", "Uri is null. Not saving to storage")
            return
        }
        val stream = requireActivity().contentResolver.openInputStream(uri)
        if (stream == null) {
            Log.e("image", "Stream is null. Not saving to storage")
            return
        }

        val imageId = kotlin.math.abs(Random.nextLong()).toString()

        storageImagesRef.child(imageId).putStream(stream)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageImagesRef.child(imageId).downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageUriStringInFragment = task.result.toString()
                    Log.d("image", "Got download uri: $storageUriStringInFragment")
                    binding.updateUserProfileButton.text = "Update Profile"
                    binding.updateUserProfileButton.isEnabled = true

                } else {
                    // Handle failures
                    // ...
                }
            }

    }

}