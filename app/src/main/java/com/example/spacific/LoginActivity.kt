package com.example.spacific

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Simple authentication logic (replace with real authentication as needed)
            if (email == "user@example.com" && password == "password123") {
                // Authentication successful, set user as authenticated
                setUserAuthenticated(true)

                // Navigate to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Authentication failed, show error message
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        textViewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Function to set the user authentication status in SharedPreferences
    private fun setUserAuthenticated(isAuthenticated: Boolean) {
        val sharedPrefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("is_authenticated", isAuthenticated).apply()
    }
}
