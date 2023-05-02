package com.example.payparking.activity


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.payparking.R
import com.example.payparking.databinding.ActivityMainBinding
import com.example.payparking.fragment.*
import com.example.payparking.fragment.BottomDashboardFragment
import com.example.payparking.fragment.DashboardFragment
import com.example.payparking.fragment.ProfileFragment
import com.example.payparking.fragment.ServicesFragment
import com.google.android.material.navigation.NavigationView

class Navigation : AppCompatActivity() {


    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frameLayout: FrameLayout

    lateinit var container: FrameLayout

    private lateinit var binding: ActivityMainBinding

    var previousMenuItem: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbarLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        frameLayout = findViewById(R.id.frameLayout)

        // Bottom Navigation
        container = findViewById(R.id.container)
        binding.bnView.setOnItemSelectedListener {

            when(it.itemId){

               R.id.locateMenuItem -> {
                   replaceFragment(MapsFragment())
                   supportActionBar?.title = "Locate"
               }

               R.id.myParkingMenuItem -> {
                   replaceFragment(BottomDashboardFragment())
                   supportActionBar?.title = "Dashboard"
               }

               R.id.accountMenuItem -> {
                   replaceFragment(ProfileFragment())
                   supportActionBar?.title = "Profile"
               }

               else -> {
                    Toast.makeText(this, "Error occured in bottom navigation", Toast.LENGTH_SHORT).show()
               }

            }
            true

        }  // End of Bottom Navigation

        // setting up toolbar
        setUpToolbar()

        // Opening Dashboard Fragment when app Load
       // openDashboard()
        openMainDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@Navigation,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId){

                R.id.home -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }

                R.id.services -> {
                    replaceFragment(ServicesFragment())
                    drawerLayout.closeDrawers()
                }

                R.id.discover -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MapsFragment())
                        .commit()

                    supportActionBar?.title = "Discover"
                    drawerLayout.closeDrawers()
                }

                R.id.myVehicle -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, BottomDashboardFragment())
                        .commit()

                    supportActionBar?.title = "My Vehicle"
                    drawerLayout.closeDrawers()
                }

                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()

                    supportActionBar?.title = "Settings"
                    drawerLayout.closeDrawers()
                }

                R.id.share -> {

                    val intent = Intent(this, Navigation::class.java)
                    startActivity(intent)

                }
                R.id.refer -> {
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
                    drawerLayout.closeDrawers()
                }

//                R.id.dashboard -> {
//                    openDashboard()
//                    drawerLayout.closeDrawers()
//                }
//
//                R.id.favorites -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frameLayout, FavoritesFragment())
//                        .commit()
//
//                    supportActionBar?.title = "Favorites"
//                    drawerLayout.closeDrawers()
//                }
//
//                R.id.profile -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frameLayout, ProfileFragment())
//                        .commit()
//
//                    supportActionBar?.title = "Profile"
//                    drawerLayout.closeDrawers()
//
//                }
//
//                R.id.about -> {
//                   supportFragmentManager.beginTransaction()
//                       .replace(R.id.frameLayout, AboutAppFragment())
//                       .commit()
//
//                   supportActionBar?.title = "About App"
//                   drawerLayout.closeDrawers()
//                }


            }

            return@setNavigationItemSelectedListener true
        }



    }  // End of OnCreate


    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){

        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"

        navigationView.setCheckedItem(R.id.home)
    }

    fun openMainDashboard(){
        val fragment = BottomDashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.home)
    }



    override fun onBackPressed() {

        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag){

            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()

        }


    }




    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()

    }









}


