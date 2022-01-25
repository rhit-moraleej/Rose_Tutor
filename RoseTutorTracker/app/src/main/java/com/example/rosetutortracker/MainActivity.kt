package com.example.rosetutortracker

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rosetutortracker.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var navView: NavigationView
    lateinit var drawerLayout: DrawerLayout

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


        val navController = findNavController(R.id.nav_host_fragment_content_main)
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
            false
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

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var menu = navView.menu
//        var menuSize = menu.size()
//        when (item.itemId) {
//            R.id.nav_tutor_home -> {
//
//
//                Log.d("tag","tutor home")
//                val change_timings = menu.add(1, menuSize, menuSize, "Change timings")
//                menu.getItem(menuSize).setIcon(R.drawable.ic_launcher_foreground)
//                menuSize = menu.size()
//
//                val change_days = menu.add(1, menuSize, menuSize, "Change days")
//                menu.getItem(menuSize).setIcon(R.drawable.ic_launcher_foreground)
//                menuSize = menu.size()
//
//                val change_location = menu.add(1, menuSize, menuSize, "Change location")
//                menu.getItem(menuSize).setIcon(R.drawable.ic_launcher_foreground)
//                menuSize = menu.size()
//
//                val change_classes = menu.add(1, menuSize, menuSize, "Change classes")
//                menu.getItem(menuSize).setIcon(R.drawable.ic_launcher_foreground)
//                menuSize = menu.size()
//
//
//
//
//                drawerLayout.openDrawer(Gravity.LEFT)
//            }
//
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//
//    }



}