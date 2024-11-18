package com.example.spacific

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Set the activity to fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Delay the splash screen for 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if the user is authenticated
            if (isUserAuthenticated()) {
                // User is logged in, navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // User is not logged in, navigate to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish() // Finish SplashScreenActivity
        }, 500) // 500ms delay for the splash screen
    }

    // Check if the user is authenticated using SharedPreferences
    private fun isUserAuthenticated(): Boolean {
        val sharedPrefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("is_authenticated", false)
    }

    // Call this function to set the authentication status after login/logout
    private fun setUserAuthenticated(isAuthenticated: Boolean) {
        val sharedPrefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("is_authenticated", isAuthenticated).apply()
    }
}
