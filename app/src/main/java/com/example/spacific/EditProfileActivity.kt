package com.example.spacific

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spacific.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageView: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var btnChangePhoto: Button
    private lateinit var btnSaveChanges: Button
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var progressBar: ProgressBar

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        profileImageView = findViewById(R.id.profile_image)
        usernameEditText = findViewById(R.id.edit_username)
        btnChangePhoto = findViewById(R.id.btn_change_photo)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        // Fetch the current user's profile from Firestore
        loadUserProfile()

        // Set click listener for Change Photo button
        btnChangePhoto.setOnClickListener {
            checkPermissionAndOpenGallery()
        }

        // Set click listener for Save Changes button
        btnSaveChanges.setOnClickListener {
            saveChanges()
        }

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userDocRef = db.collection("users").document(currentUser.uid)

            // Fetch the user's profile info
            userDocRef.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val username = document.getString("username")
                    val photoUrl = document.getString("profilePhotoUrl")

                    // Set the profile data
                    usernameEditText.setText(username)
                    if (photoUrl != null) {
                        Picasso.get().load(photoUrl).into(profileImageView)
                    }
                }
            }
        }
    }

    private fun checkPermissionAndOpenGallery() {
        // Check and request permission if necessary
        if (androidx.core.content.ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    // Request permission launcher for runtime permissions
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
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

                // Upload the image to Firebase Storage
                uploadProfileImage(selectedImageUri)
            }
        }
    }

    private fun uploadProfileImage(uri: Uri) {
        val storageRef: StorageReference = storage.reference.child("profile_images/${auth.currentUser?.uid}")
        showProgressBar(true)  // Show progress bar during upload

        storageRef.putFile(uri)
            .addOnSuccessListener {
                // Get the image URL
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // Save the image URL to Firestore
                    saveProfileImageUrl(downloadUrl.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                showProgressBar(false)  // Hide progress bar after upload completes
            }
    }

    private fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun saveProfileImageUrl(photoUrl: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userDocRef = db.collection("users").document(currentUser.uid)

            // Update Firestore with the new profile photo URL
            userDocRef.update("profilePhotoUrl", photoUrl)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile photo updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update profile photo", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveChanges() {
        val newUsername = usernameEditText.text.toString().trim()

        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userDocRef = db.collection("users").document(currentUser.uid)

            // Update Firestore with the new username
            userDocRef.update("username", newUsername)
                .addOnSuccessListener {
                    Toast.makeText(this, "Changes saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
