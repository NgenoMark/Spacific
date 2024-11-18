package com.example.spacific

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BabadogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_babadogo)

        // Hide the ActionBar
        supportActionBar?.hide()

        // Enable edge-to-edge display by applying system bar insets
        val mainView = findViewById<View>(R.id.main)  // Ensure R.id.main is the root view in activity_simton layout
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            insets
        }

        // Get reference to the Button
        val messageButton: Button = findViewById(R.id.messageButton)

        // Set an OnClickListener to the Button
        messageButton.setOnClickListener {
            // When the button is clicked, start SimtonchatActivity
            val intent = Intent(this, BabadogochatActivity::class.java)
            startActivity(intent)
        }
    }
}
