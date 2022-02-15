package com.example.rosetutortracker.setup

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.rosetutortracker.BuildConfig
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.R
import com.example.rosetutortracker.databinding.ActivityMainBinding
import com.example.rosetutortracker.databinding.FragmentUserDetailsBinding
import com.example.rosetutortracker.models.Student
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.utils.KeyBoardUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class UpdateUserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var navBinding: ActivityMainBinding
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
        Log.d("hi","hi")
        studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        binding.cancelUserProfileButton.setOnClickListener {
            findNavController().popBackStack()
            FirebaseAuth.getInstance().signOut()
        }

        updateView()

        binding.userClass.setText(studentModel.student!!.classYear.toString())
        binding.userMajor.setText(studentModel.student!!.major)
        Log.d("image",(studentModel.student!!.storageUriString))
        binding.userProfile.load(studentModel.student!!.storageUriString) {
            crossfade(true)
            size(400, 400)
        }

        binding.updateUserProfileButton.setOnClickListener {
            Log.d("Student", "StudentModel before setup: ${studentModel.student}")
            val userclass: Int = binding.userClass.text.toString().toInt()
            val refStudent = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)

            val major = binding.userMajor.text.toString()
            refStudent.document(Firebase.auth.uid!!).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val temp = Student.from(it.result!!)
                        Log.d("stu","$temp")
                        temp!!.major = major
                        temp.classYear = userclass
                        temp.storageUriString = if (storageUriStringInFragment=="") studentModel.student!!.storageUriString else storageUriStringInFragment
                        studentModel.update(temp)
                    }
                }



            Log.d("image", storageUriStringInFragment)
            KeyBoardUtils.hideKeyboard(requireView(), requireActivity())
            findNavController().navigate(R.id.nav_home)
        }

        binding.userEditUploadPhotoButton.setOnClickListener {
            showPictureDialog()
        }

        return binding.root
    }

    private fun updateView() {
        val temp = studentModel.student // for some reason temp loses its values
        //Log.d("update", "temp while updating view: ${temp?.name}, ${temp?.email}")
        name = studentModel.student?.name!!
        email = studentModel.student?.email!!
        binding.userName.text = getString(R.string.name, studentModel.student?.name)
        binding.userEmail.text = getString(R.string.email_edit, studentModel.student?.email)
        Log.d("image",(studentModel.student!!.storageUriString))
    }

    private fun showPictureDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose a photo source")
        builder.setMessage("Would you like to take a new picture?\nOr choose an existing one?")
        builder.setPositiveButton("Take Picture") { _, _ ->
            binding.updateUserProfileButton.isEnabled = false
            binding.updateUserProfileButton.text = getString(R.string.load_img)
            takeImage()
        }

        builder.setNegativeButton("Choose Picture") { _, _ ->
            binding.updateUserProfileButton.isEnabled = false
            binding.updateUserProfileButton.text = getString(R.string.load_img)
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
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
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