package com.example.rosetutortracker.ui

import com.example.rosetutortracker.models.BaseViewModel
import com.example.rosetutortracker.models.Tutor

class FavTutorDetailFragment : TutorDetailFragment() {
    override var usedModel: BaseViewModel<Tutor> = homeModel

}