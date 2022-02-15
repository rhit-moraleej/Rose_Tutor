package com.example.rosetutortracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rosetutortracker.R
import com.example.rosetutortracker.adaptor.FavTutorAdaptor
import com.example.rosetutortracker.databinding.FragmentHomeBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.utils.NotificationUtils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adaptor: FavTutorAdaptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adaptor = FavTutorAdaptor(this)
        adaptor.getFavTutors()
        adaptor.model.firstTime = true
        adaptor.model.addListener("", requireContext())
        //option to drag and drop items
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        NotificationUtils.createChannel(requireContext())

        val studentModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        val navView: NavigationView = requireActivity().findViewById(R.id.nav_view)
        val navHeaderView = navView.getHeaderView(0)
        val userName: TextView = navHeaderView.findViewById(R.id.user_name)
        val userEmail: TextView = navHeaderView.findViewById(R.id.user_email)
        val userProfile: ImageView = navHeaderView.findViewById(R.id.user_profile)
        Log.d("navView", userName.text.toString())
        Log.d("navView", userEmail.text.toString())
        userName.text = studentModel.student?.name ?: ""
        userEmail.text = studentModel.student?.email ?: ""
        userProfile.load(studentModel.student!!.storageUriString) {
            crossfade(true)
            transformations(CircleCropTransformation())
            size(200, 200)
        }

        Log.d("rr", Firebase.auth.currentUser!!.uid)

        return binding.root
    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val startPos = viewHolder.adapterPosition
            val endPos = target.adapterPosition
            Collections.swap(adaptor.model.list, startPos, endPos)
            recyclerView.adapter?.notifyItemMoved(startPos, endPos)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

    }

}