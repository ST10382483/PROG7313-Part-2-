package com.neha.st10442032loginreghome


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link this activity to the login screen layout
        setContentView(R.layout.activity_login)

        // Get references to the UI elements
        val emailField = findViewById<TextInputEditText>(R.id.loginEmail)
        val passwordField = findViewById<TextInputEditText>(R.id.loginPassword)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val goToRegister = findViewById<TextView>(R.id.goToRegister)

        // When the login button is tapped
        loginButton.setOnClickListener {

            // Get the text the user typed and remove extra spaces
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            //  that email is not empty
            if (email.isEmpty()) {
                emailField.error = "Please enter your email"
                return@setOnClickListener
            }
            //  that password is not emptyy
            if (password.isEmpty()) {
                passwordField.error = "Please enter your password"
                return@setOnClickListener
            }

            // Navigate to the Home screen and pass the email along
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_EMAIL", email)
            startActivity(intent)
            finish() // Remove login screen from back stack so user can't go back to it
        }

        // When Register is tapped should send user to go to the Register screen
        goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}