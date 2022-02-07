package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel
import com.example.rosetutortracker.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TutorViewModel: ViewModel() {
    private lateinit var ref: DocumentReference
    var tutor: Tutor? = null

    fun hasCompletedSetup() = tutor?.hasCompletedSetup ?: false
    fun getOrMakeUser(observer: () -> Unit){
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(Firebase.auth.uid!!)
        if(tutor != null){ //get
            observer()
        }else{ // make
            ref.get().addOnSuccessListener{ snapshot: DocumentSnapshot ->
                if(snapshot.exists()){
                    tutor = snapshot.toObject(Tutor::class.java)
                }else{
                    tutor = Tutor()
                    ref.set(tutor!!)
                }
                observer()
            }
        }
    }

    fun update(newHasCompletedSetup: Boolean){
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(Firebase.auth.uid!!)
        val ref2 = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT).document(Firebase.auth.uid!!)
        if(tutor != null){
            with(tutor!!){
                hasCompletedSetup = newHasCompletedSetup
                ref.set(this)
                ref2.update("tutor", true)
            }
        }
    }

    fun updateTutorCourses(list: ArrayList<String>) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(Firebase.auth.uid!!)
        ref.update("courses", list)
    }
}