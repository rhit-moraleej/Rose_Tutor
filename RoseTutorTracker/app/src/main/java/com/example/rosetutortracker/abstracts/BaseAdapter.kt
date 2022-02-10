package com.example.rosetutortracker.abstracts

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(val fragment: Fragment) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    abstract val model: BaseViewModel<T>
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(model.getListAt(position))
    }

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return setViewHolder(parent, viewType)
    }

    override fun getItemCount() = model.size()

}
