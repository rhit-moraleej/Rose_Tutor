package com.example.rosetutortracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.models.BaseViewModel
import com.example.rosetutortracker.models.HomeViewModel

abstract class BaseAdapter<T>(val fragment: Fragment,
                           viewModel: BaseViewModel<T>,
                           ): RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity())[viewModel :: class.java]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model.getListAt(position))
    }

    override fun getItemCount() = model.size()

    abstract inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(item: T)
    }
}