package com.example.rosetutortracker

import android.os.Bundle
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
import com.example.rosetutortracker.models.Tutor
import com.example.rosetutortracker.models.TutorViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.appBarMain.fab.hide()
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navView.bringToFront()
        //navView.setNavigationItemSelectedListener(this)

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
            navView.menu[3].isVisible = true
            navView.menu[4].isVisible = true
            navView.menu[5].isVisible = true
            navView.menu[6].isVisible = true
            drawerLayout.closeDrawer(GravityCompat.START)
            checkTutorStatus()
            false
        }

        navView.menu[7].setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut();
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.nav_login)
            false
        }

        navView.menu[3].setOnMenuItemClickListener {
            val startpicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Start time")
                    .build()
            startpicker.addOnPositiveButtonClickListener {
                val s = String.format("Time: %d:%02d",startpicker.hour,startpicker.minute)
                val endpicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select End time")
                        .build()
                endpicker.addOnPositiveButtonClickListener {
                    val s = String.format("Time: %d:%02d",endpicker.hour,endpicker.minute)

                }
                endpicker.show(supportFragmentManager,"tag")
            }
            startpicker.show(supportFragmentManager,"tag")
            false
        }

        navView.menu[4].setOnMenuItemClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.nav_tutor_edit_day_time)
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
                navController.navigate(R.id.nav_tutor_setup)
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
