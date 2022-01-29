package com.example.rosetutortracker.models

class HomeViewModel: BaseViewModel<Tutor>()  {

    fun containsTutor(tutor: Tutor): Boolean{
        return this.list.contains(tutor)
    }

    fun addTutor(tutor: Tutor?){
        if (tutor != null) {
            this.list.add(tutor)
        }
    }

}