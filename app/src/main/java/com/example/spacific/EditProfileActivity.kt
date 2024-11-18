package com.example.spacific

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spacific.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageView: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var btnChangePhoto: Button
    private lateinit var btnSaveChanges: Button
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge mode for a full-screen experience
        enableEdgeToEdge()

        // Inflate the layout using ViewBinding
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        profileImageView = findViewById(R.id.profile_image)
        usernameEditText = findViewById(R.id.edit_username)
        btnChangePhoto = findViewById(R.id.btn_change_photo)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        // Set click listener for Change Photo button
        btnChangePhoto.setOnClickListener {
            openGallery()
        }

        // Set click listener for Save Changes button
        btnSaveChanges.setOnClickListener {
            saveChanges()
        }

        // Apply window insets to adjust padding when system bars (status bar, navigation bar) are visible
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Return the insets so that the system can apply them
        }
    }

    private fun openGallery() {
        // Create an Intent to pick an image from the gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                // Set the selected image to the ImageView
                profileImageView.setImageURI(selectedImageUri)
            }
        }
    }

    private fun saveChanges() {
        val newUsername = usernameEditText.text.toString()

        // Save the new username and profile photo (if any)
        // You can use SharedPreferences, a database, or any other method to save the data
        Toast.makeText(this, "Changes saved!", Toast.LENGTH_SHORT).show()
    }
}
