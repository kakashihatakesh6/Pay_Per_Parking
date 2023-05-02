package com.example.payparking.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.payparking.R
import com.example.payparking.fragment.DashboardFragment
import com.example.payparking.fragment.MapsFragment
import com.example.payparking.fragment.SuggestFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference

class MainActivity: AppCompatActivity() {

    lateinit var navigationView: NavigationView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Firebase

        navigationView = findViewById(R.id.navigationView)
        bottomNavigationView = findViewById(R.id.bnView)
        toolbar = findViewById(R.id.toolbarLayout)
        frameLayout = findViewById(R.id.frameLayout)

        // Setting Up Toolbar


        // Navigation Drawer



        //
        setCurrentFragment(DashboardFragment(), "Dashboard")

       //  bottom navigation
       bottomNavigationView.setOnNavigationItemSelectedListener {
           when(it.itemId){

               R.id.locateMenuItem -> {
                   setCurrentFragment(MapsFragment(), "Locate")
               }

               R.id.myParkingMenuItem -> {
                   setCurrentFragment(DashboardFragment(), "Dashboard")
               }
               R.id.accountMenuItem -> {
                   setCurrentFragment(SuggestFragment(), "Suggest")
               }


           }
           return@setOnNavigationItemSelectedListener true
       }

    }  // End of OnCreateView

    fun setCurrentFragment(fragment: Fragment, title: String){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = title

    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag){
            !is DashboardFragment -> setCurrentFragment(DashboardFragment(), "Dashboard")

            else -> super.onBackPressed()

        }
    }






} // End Of Main Activity