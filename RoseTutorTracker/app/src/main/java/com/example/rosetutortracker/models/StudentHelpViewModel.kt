package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel

class StudentHelpViewModel:ViewModel() {
    var students = ArrayList<Student>()
    var currentPos = 0

    fun getStudentAt(pos: Int): Student {
        return students[pos]
    }

    fun getCurrentStudent(): Student {
        return students[currentPos]
    }

    fun updatePos(pos: Int){
        currentPos = pos
    }

    fun resolveCurrentStudent() {
        students.removeAt(currentPos)
        currentPos = 0
    }

    fun size(): Int {
        return students.size
    }
}