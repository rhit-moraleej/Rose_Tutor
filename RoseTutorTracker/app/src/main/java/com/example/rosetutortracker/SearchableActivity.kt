package com.example.rosetutortracker

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rosetutortracker.databinding.FragmentFindTutorListBinding

class SearchableActivity: AppCompatActivity() {
    private lateinit var binding: FragmentFindTutorListBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentFindTutorListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Verify the action and get the query
//        if (Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                Log.d("rr", query)
//            }
//        }
//
//        binding.tutorSearch.isSubmitButtonEnabled = true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFindTutorListBinding.inflate(layoutInflater)
        binding.tutorSearch.isSubmitButtonEnabled
        setContentView(binding.root)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("rr", query)
            }
        }
    }
//    override fun onSearchRequested(): Boolean {
//        return super.onSearchRequested()
//    }
//override fun onSearchRequested(): Boolean {
//    val jargon: Boolean = intent.getBundleExtra(SearchManager.APP_DATA)?.getBoolean(JARGON) ?: false
//    val appData = Bundle().apply {
//        putBoolean(JARGON, true)
//    }
//    startSearch(null, false, appData, false)
//    return true
//}
}