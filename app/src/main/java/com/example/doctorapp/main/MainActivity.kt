package com.example.doctorapp.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.R
import com.example.doctorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    val navController by lazy {  findNavController(R.id.nav_host_fragment_content_main)}
    private val handler = Handler(Looper.myLooper()!!)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login, R.id.nav_chats,R.id.nav_add_user,R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navigationViewActions()
    }
    private fun navigationViewActions() {
        binding.navView.setNavigationItemSelectedListener {
            handler.postDelayed({
                when (it.itemId) {
                    R.id.nav_home -> {
                       navController.navigate(R.id.nav_home)
                    }
                    R.id.nav_chats->{
                        navController.navigate(R.id.nav_chats)
                    }
                    R.id.logout->{
                        vm.logout().apply {
                            navController.navigate(R.id.nav_login)
                            this@MainActivity.recreate()
                        }

                    }
                }

            }, 500)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}