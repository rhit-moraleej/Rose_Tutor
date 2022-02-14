package com.example.rosetutortracker.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rosetutortracker.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RatingTutorViewModel : ViewModel() {
    var tutor = Tutor()
    lateinit var ref: CollectionReference

    fun tutorName() = tutor.studentInfo.name

    fun getTutorInfo(id: String) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
        ref.document(id).get()
            .addOnCompleteListener { call ->
                if (call.isSuccessful) {
                    val student = Student.from(call.result!!)
                    searchTutor(id, student)
                } else {
                    Log.d("rating", "something broke")
                }
            }
    }

    private fun searchTutor(id: String, student: Student) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR)
        ref.document(id).get()
            .addOnCompleteListener { call ->
                if (call.isSuccessful) {
                    tutor = Tutor.from(call.result!!)
                    tutor.studentInfo = student
                }
            }
    }

    fun addRating(rating: Double) {
        tutor.addRating(rating)
        ref.document(tutor.id).set(tutor)
    }
}
