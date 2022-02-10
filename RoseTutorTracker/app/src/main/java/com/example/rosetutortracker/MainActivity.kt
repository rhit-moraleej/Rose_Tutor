package com.example.rosetutortracker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rosetutortracker.databinding.ActivityMainBinding
import com.example.rosetutortracker.models.StudentViewModel
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var tutorModel: TutorViewModel
    private lateinit var studentModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tutorModel = ViewModelProvider(this)[TutorViewModel::class.java]
        studentModel = ViewModelProvider(this)[StudentViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // This does not work
        val auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                tutorModel.getUser {
                    Log.d("rr", "AuthState changed to ${it.currentUser?.uid}")
                    Log.d("rr", tutorModel.tutor.toString())
                    Log.d("rr", tutorModel.hasCompletedSetup().toString())
                    if (tutorModel.tutor != null && tutorModel.hasCompletedSetup()) {
                        navView.menu[2].title = "Student Requests"
                        navView.menu[3].isVisible = true
                        navView.menu[4].isVisible = true
                        navView.menu[5].isVisible = true
                        navView.menu[6].isVisible = true
                        Log.d("rr", "$tutorModel.tutor.toString() zzzzzzzz")
                    }
                }
            }
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.appBarMain.fab.hide()
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navView.bringToFront()

        navView.menu[3].isVisible = false
        navView.menu[4].isVisible = false
        navView.menu[5].isVisible = false
        navView.menu[6].isVisible = false


        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_find_tutor, R.id.nav_tutor_home
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu[2].setOnMenuItemClickListener {
            navView.menu[2].title = "Student Requests"
            navView.menu[3].isVisible = true
            navView.menu[4].isVisible = true
            navView.menu[5].isVisible = true
            navView.menu[6].isVisible = true
            drawerLayout.closeDrawer(GravityCompat.START)
            checkTutorStatus()
            false
        }

        navView.menu[7].setOnMenuItemClickListener {
            tutorModel.tutor = null
            studentModel.student = null
            FirebaseAuth.getInstance().signOut()
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.nav_login)
            false
        }

        navView.menu[3].setOnMenuItemClickListener {
            Snackbar.make(navView, "PLACEHOLDER NAV ITEM", Snackbar.LENGTH_LONG)
                .setAction("Continue") {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.popBackStack()
                }.show()
            false
        }

        navView.menu[4].setOnMenuItemClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.nav_tutor_edit_day_time)
            false
        }

        navView.menu[5].setOnMenuItemClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.nav_tutor_edit_location)
            false
        }
    }

    private fun checkTutorStatus() {
        val tutorModel = ViewModelProvider(this)[TutorViewModel::class.java]
        tutorModel.getOrMakeUser {
            if (tutorModel.hasCompletedSetup()) {
                navController.navigate(R.id.nav_home)
            } else {
                tutorModel.tutor = Tutor()
                navController.navigate(R.id.nav_course_tutor_setup)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
