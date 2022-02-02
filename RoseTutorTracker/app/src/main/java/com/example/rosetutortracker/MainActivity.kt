package com.example.rosetutortracker



//import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
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
import com.example.rosetutortracker.models.Student
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import rosefire.rosefire.Rosefire
import rosefire.rosefire.RosefireResult


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
//    val signinLauncher = registerForActivityResult(
//        FirebaseAuthUIActivityResultContract()
//    ) { /* empty since the auth listener already responds .*/ }

    private val auth = FirebaseAuth.getInstance()
    private var authFlag = false
    lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private val RC_ROSEFIRE_SIGN_IN = 1001
    private val userRef = FirebaseFirestore.getInstance().collection("Students")


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
        initializeListeners()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }


    private fun initializeListeners() {
        //initialize authStateListener to handle user login
        authStateListener = FirebaseAuth.AuthStateListener {auth: FirebaseAuth ->
            val user = auth.currentUser
            Log.d(Constants.TAG, "Auth Listener: user = $user")
            if (user != null){
                Log.d(Constants.TAG, "UID = ${user.uid}")
                userRef.document(user.uid).get().addOnSuccessListener {
                    if(!it.exists()){
                        //if this is a new user, add a user object to the database for them
                        Log.d(Constants.TAG, "Adding new user for first login")
                        userRef.document(user.uid).set(Student("Name1","name1@gmail.com",2024,false))
                    }
                }
            } else if (!authFlag) {
                //auth flag is used to prevent login screen from launching twice
                authFlag = true
                launchLoginUI()
            }
        }
    }

    private fun launchLoginUI() {
        val signInIntent: Intent = Rosefire.getSignInIntent(this, getString(R.string.rosefire_key))
        startActivityForResult(intent, RC_ROSEFIRE_SIGN_IN)
    }

    //catches the result of the RoseFire sign in intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_ROSEFIRE_SIGN_IN){
            val result: RosefireResult = Rosefire.getSignInResultFromIntent(data)
            if (!result.isSuccessful){
                Log.d(Constants.TAG, "The user cancelled the login")
                authFlag = false
                return
            }
            //if the task is not successful, send a toast, otherwise let the authStateListener do its function
            auth.signInWithCustomToken(result.token).addOnCompleteListener {task ->
                Log.d(Constants.TAG, "signInWithCustomToken:onComplete:" + task.isSuccessful)
                if (!task.isSuccessful) {
                    Log.w(Constants.TAG, "signInWithCustomToken", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    authFlag = false
                } else authFlag = true
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
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
