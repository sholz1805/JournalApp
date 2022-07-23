package com.example.journal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.journal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        val bottomNavBar = binding.bottomNavBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homePage,
                R.id.search,
                R.id.archivedJournals,
                R.id.profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id == R.id.login || destination.id == R.id.register) {
                bottomNavBar.visibility = View.GONE
            } else bottomNavBar.visibility = View.VISIBLE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}