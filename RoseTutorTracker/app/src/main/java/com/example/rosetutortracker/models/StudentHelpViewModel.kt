package com.example.rosetutortracker.models

class StudentHelpViewModel: BaseViewModel<Student>() {
    fun resolveCurrentStudent() {
        removeCurrent()
    }
}