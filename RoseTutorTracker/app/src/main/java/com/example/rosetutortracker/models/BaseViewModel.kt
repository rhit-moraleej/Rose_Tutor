package com.example.rosetutortracker.models

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    var list = ArrayList<T>()
    private var currPos = 0

    fun getListAt(pos: Int) = list[pos]
    fun getCurrent() = list[currPos]
    fun setListAt(pos: Int, item: T) {
        list[pos] = item
    }

    fun updatePos(pos: Int) {
        currPos = pos
    }

    fun removeCurrent() {
        list.removeAt(currPos)
        currPos = 0
    }

    fun size() = list.size
}