package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel
import com.example.rosetutortracker.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentViewModel: ViewModel() {
    private var ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT).document(Firebase.auth.uid!!)
    var student: Student? = null

    fun hasCompletedSetup() = student?.hasCompletedSetup ?: false
    fun getOrMakeUser(observer: () -> Unit){
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT).document(Firebase.auth.uid!!)
        if(student != null){ //get
            observer()
        }else{ // make
            ref.get().addOnSuccessListener{ snapshot: DocumentSnapshot ->
                if(snapshot.exists()){
                    student = snapshot.toObject(Student::class.java)
                }else{
                    student = Student()
                    ref.set(student!!)
                }
                observer()
            }
        }
    }

    fun update(newName: String, newClassYear: Int, newHasCompletedSetup: Boolean){
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT).document(Firebase.auth.uid!!)
        if(student != null){
            with(student!!){
                name = newName
                classYear = newClassYear
                isTutor = false
                hasCompletedSetup = newHasCompletedSetup
                ref.set(student!!)
            }
        }
    }
}