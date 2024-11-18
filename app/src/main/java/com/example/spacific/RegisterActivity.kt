package com.example.spacific

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Simulate registration (replace with real registration logic)
                if (registerUser(name, email, password)) {
                    // Set user as authenticated after successful registration
                    setUserAuthenticated(true)

                    // Navigate to MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Show error if registration failed
                    Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // Mock registration function (replace with actual registration logic)
    private fun registerUser(name: String, email: String, password: String): Boolean {
        // Here, you would typically call your backend or Firebase to register the user
        // For this example, we simply return true as if registration succeeded
        return true
    }

    // Function to set the user authentication status in SharedPreferences
    private fun setUserAuthenticated(isAuthenticated: Boolean) {
        val sharedPrefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("is_authenticated", isAuthenticated).apply()
    }
}
