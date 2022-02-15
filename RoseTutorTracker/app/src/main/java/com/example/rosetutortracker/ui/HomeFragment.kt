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
import androidx.recyclerview.widget.ItemTouchHelper.*
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

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adaptor: FavTutorAdaptor
    private lateinit var model: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        model = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        adaptor = FavTutorAdaptor(this)
        adaptor.getFavTutors()
        adaptor.model.firstTime = true
        adaptor.model.addListener("", requireContext())
        //option to drag and drop items
//        val itemTouchHelper = ItemTouchHelper(simpleCallback)
//        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
//        adaptor.differ.submitList(adaptor.model.list)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)



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

    private val itemTouchHelper by lazy {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, (LEFT or RIGHT)) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adaptor = recyclerView.adapter as FavTutorAdaptor
                    val startPos = viewHolder.adapterPosition
                    val endPos = target.adapterPosition

                    adaptor.moveItem(startPos, endPos)
                    adaptor.notifyItemMoved(startPos, endPos)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when(direction){
                        LEFT -> {
                            adaptor.removeTutor(viewHolder.adapterPosition)
                        }
                        RIGHT -> {
                            adaptor.getDetails(viewHolder.adapterPosition)
                        }
                    }
                }

                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.scaleY = 1.3f
                        viewHolder?.itemView?.alpha = 0.7f
                    }
                }


                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.scaleY = 1.0f
                    viewHolder.itemView.alpha = 1.0f
                }
            }
        ItemTouchHelper(itemTouchCallback)
    }


}