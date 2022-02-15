package com.example.rosetutortracker.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rosetutortracker.adaptor.StudentHelpAdapter
import com.example.rosetutortracker.databinding.FragmentTutorHomeBinding
import com.example.rosetutortracker.utils.NotificationUtils
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator


class TutorHomeFragment : Fragment() {
    private lateinit var binding: FragmentTutorHomeBinding
    private lateinit var adaptor: StudentHelpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTutorHomeBinding.inflate(inflater, container, false)
        adaptor = StudentHelpAdapter(this)
//        adaptor.getMessages()
        adaptor.model.firstTime = true
        adaptor.addListener(fragmentName, requireContext())

        val alphaAdapter = AlphaInAnimationAdapter(adaptor)
        binding.recyclerView.adapter = ScaleInAnimationAdapter(alphaAdapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        NotificationUtils.createChannel(requireContext())

//        binding.recyclerView.layoutManager = FadeInLinearLayoutManager(context)

        binding.recyclerView.itemAnimator = LandingAnimator()
        binding.recyclerView.itemAnimator?.apply {
            addDuration = 1000
            removeDuration = 1000
            moveDuration = 1000
            changeDuration = 100
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        adaptor.removeListener(fragmentName)
    }

    companion object {
        const val fragmentName: String = "TutorHomeFragment"
    }
}

open class FadeInLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private val enterInterpolator = AnticipateOvershootInterpolator(2f)

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        val h = 400f
        // if index == 0 item is added on top if -1 it's on the bottom
        child.translationY = if(index == 0) -h else h
        // begin animation when view is laid out
        child.alpha = 0.3f
        child.animate().translationY(0f).alpha(1f)
            .setInterpolator(enterInterpolator)
            .setDuration(1000L)
    }
}

