package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel
import com.example.rosetutortracker.Constants
import com.google.common.collect.Iterables.addAll
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Collections.addAll

class TutorViewModel: ViewModel() {
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(Firebase.auth.uid!!)
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

    fun update(newCourses: String, newLocation: String, newHasCompletedSetup: Boolean){
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(Firebase.auth.uid!!)
        if(tutor != null){
            var courses =newCourses.split(", ")
            with(tutor!!){
                courses = courses
                location = newLocation
                hasCompletedSetup = newHasCompletedSetup
                ref.set(tutor!!)
            }
        }
    }
}