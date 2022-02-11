package com.example.rosetutortracker.models

import android.util.Log
import com.example.rosetutortracker.Constants
import com.example.rosetutortracker.abstracts.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentViewModel : BaseViewModel<Tutor>() {
    private lateinit var ref: DocumentReference
    var student: Student? = null
    var studentTemp: Student? = null
    var tutorToSendMessage: String = ""

    fun containsTutor(tutor: Tutor): Boolean {
        return student?.favoriteTutors?.contains(tutor.id) ?: false
    }

    fun addTutor(tutor: Tutor?) {
        if (tutor != null) {
            Log.d(Constants.TAG, "Adding: ${tutor.id}")
            student?.favoriteTutors?.add(tutor.id)
            list.add(tutor)
            Log.d(Constants.TAG, "List of favorite tutors: ${student?.favoriteTutors}")
            updateFavs()
        }
    }

    fun setupFavs(function: () -> Unit) {
        Log.d(Constants.TAG, "Getting favs")
        val refStudent = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
        list.clear()
        for (tutor in student?.favoriteTutors!!) {
            refStudent.document(tutor).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val tutorStudentInfo = Student.from(it.result!!)
                        searchTutor(tutor, tutorStudentInfo, function)
                    }
                    function()
                }
        }
        Log.d(Constants.TAG, "Current fav list: $list")
    }

    private fun searchTutor(id: String, student: Student, function: () -> Unit) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_TUTOR).document(id)
        ref.get()
            .addOnCompleteListener { doc ->
                if (doc.isSuccessful) {
                    val tutor = Tutor.from(doc.result!!)
                    Log.d("rr", "tutor: $tutor")
                    tutor.studentInfo = student
                    list.add(tutor)
                    Log.d(Constants.TAG, "List after tutor search: $list")
                } else {
                    Log.d("rr", "error happened")
                }
                function()
            }
    }

    fun hasCompletedSetup() = student?.hasCompletedSetup ?: false
    fun getOrMakeUser(observer: () -> Unit) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
            .document(Firebase.auth.uid!!)
        if (student != null) { //get
            Log.d("rr", "$student")
            observer()
        } else { // make
            ref.get().addOnSuccessListener { snapshot: DocumentSnapshot ->
                if (snapshot.exists()) {
                    student = snapshot.toObject(Student::class.java)
                } else {
                    student = Student()
//                    ref.set(student!!)
                }
                observer()
            }
        }
    }

    private fun updateFavs() {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
            .document(Firebase.auth.uid!!)
        ref.update("favoriteTutors", student?.favoriteTutors)
    }

    fun update(newName: String, newEmail: String, newMajor: String, newClassYear: Int, newHasCompletedSetup: Boolean, newStorageUriString: String) {
        ref = Firebase.firestore.collection(Constants.COLLECTION_BY_STUDENT)
            .document(Firebase.auth.uid!!)
        Log.d("update", "$newName, $newEmail, $newMajor, $newClassYear")
        if (student != null) {
            with(student!!) {
                name = newName
                email = newEmail
                major = newMajor
                classYear = newClassYear
                favoriteTutors = ArrayList()
                isTutor = false
                hasCompletedSetup = newHasCompletedSetup
                storageUriString = newStorageUriString
                Log.d("update", "$student")
                ref.set(this)
            }
        }
    }

    fun removeTutor(tutor: Tutor) {
        list.remove(tutor)
        student?.favoriteTutors?.remove(tutor.id)
    }
}