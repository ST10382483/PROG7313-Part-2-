package com.neha.st10442032loginreghome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class RegisterActivity : AppCompatActivity() {

    // Stores the URI (location) of the image the user picks from their gallery
    private var selectedImageUri: Uri? = null

    // Sets up the image picker — waits for the user to select a photo from their gallery
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // If the user successfully picked an image
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            // Display the selected image in the profile ImageView
            val profileImageView = findViewById<ImageView>(R.id.profileImageView)
            profileImageView.setImageURI(selectedImageUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link this activity to the register screen layout
        setContentView(R.layout.activity_register)

        // Get references to all UI elements on the screen
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)
        val usernameField = findViewById<TextInputEditText>(R.id.registerUsername)
        val nationalityDropdown = findViewById<AutoCompleteTextView>(R.id.nationalityDropdown)
        val emailField = findViewById<TextInputEditText>(R.id.registerEmail)
        val phoneField = findViewById<TextInputEditText>(R.id.registerPhone)
        val passwordField = findViewById<TextInputEditText>(R.id.registerPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val goToLogin = findViewById<TextView>(R.id.goToLogin)

        // List of Southern African countries with flag emojis for the dropdown
        val countries = listOf(
            "🇿🇦 South Africa",
            "🇿🇼 Zimbabwe",
            "🇧🇼 Botswana",
            "🇿🇲 Zambia",
            "🇳🇦 Namibia",
            "🇲🇿 Mozambique",
            "🇲🇼 Malawi",
            "🇸🇿 Eswatini",
            "🇱🇸 Lesotho"
        )

        // Connect the countries list to the dropdown so it shows as selectable options
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        nationalityDropdown.setAdapter(adapter)

        // When "Choose Profile Photo" is tapped, open the device gallery
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        // When "Create Account" is tapped
        registerButton.setOnClickListener {
            // Get the text from each field and remove extra spaces
            val username = usernameField.text.toString().trim()
            val nationality = nationalityDropdown.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate each field — show an error and stop if anything is missing
            if (username.isEmpty()) { usernameField.error = "Please enter a username"; return@setOnClickListener }
            if (nationality.isEmpty()) { nationalityDropdown.error = "Please select your nationality"; return@setOnClickListener }
            if (email.isEmpty()) { emailField.error = "Please enter your email"; return@setOnClickListener }
            if (phone.isEmpty()) { phoneField.error = "Please enter your cell number"; return@setOnClickListener }
            if (password.isEmpty()) { passwordField.error = "Please enter a password"; return@setOnClickListener }
            if (password.length < 6) { passwordField.error = "Password must be at least 6 characters"; return@setOnClickListener }

            // All fields are valid — navigate to Home screen and pass the user's details
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_USERNAME", username)       // Pass username
            intent.putExtra("USER_NATIONALITY", nationality) // Pass nationality
            intent.putExtra("USER_EMAIL", email)             // Pass email
            intent.putExtra("USER_IMAGE_URI", selectedImageUri?.toString()) // Pass image
            startActivity(intent)
            finish() // Remove register screen from back stack
        }

        // When "Login" is tapped, go back to the previous screen
        goToLogin.setOnClickListener { finish() }
    }
}  