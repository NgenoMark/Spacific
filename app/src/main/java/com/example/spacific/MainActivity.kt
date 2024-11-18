package com.example.spacific

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.spacific.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Find NavHostFragment and NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configure AppBar and BottomNavigationView
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        // Listen for destination changes to dynamically update menu visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu() // Trigger a refresh of the options menu
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Get the current destination
        val currentDestination = navController.currentDestination?.id
        val isFirstFragment = currentDestination == R.id.FirstFragment // Replace with your FirstFragment ID
        val isThirdFragment = currentDestination == R.id.ThirdFragment // Replace with your ThirdFragment ID

        // Show/Hide menu items based on conditions
        menu.findItem(R.id.action_settings)?.isVisible = false
        menu.findItem(R.id.nav_second_activity)?.isVisible = isFirstFragment
        menu.findItem(R.id.nav_third_activity)?.isVisible = isFirstFragment

        // Show menu options only on the ThirdFragment
        menu.findItem(R.id.menu_settings)?.isVisible = isThirdFragment
        menu.findItem(R.id.menu_edit_profile)?.isVisible = isThirdFragment

        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings action
                true
            }
            R.id.nav_second_activity -> {
                // Navigate to the second activity
                startActivity(Intent(this, SecondActivity::class.java))
                true
            }
            R.id.nav_third_activity -> {
                // Navigate to the third activity
                startActivity(Intent(this, ThirdActivity::class.java))
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true

            }
            R.id.menu_edit_profile -> {
                startActivity(Intent(this, EditProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
