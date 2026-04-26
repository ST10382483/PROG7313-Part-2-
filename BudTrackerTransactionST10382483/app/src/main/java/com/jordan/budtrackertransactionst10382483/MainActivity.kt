package com.jordan.budtrackertransactionst10382483

import android.content.Intent // Used to move between activities (screens)
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// MainActivity represents the Transactions screen of the app
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its XML layout file
        setContentView(R.layout.activity_main)

        // Link the button from XML to Kotlin using its ID
        val btnViewGraphs = findViewById<Button>(R.id.btnViewGraphs)

        // Set what happens when the button is clicked
        btnViewGraphs.setOnClickListener {

            // Create an Intent to move from MainActivity to GraphsActivity
            val intent = Intent(this, GraphsActivity::class.java)

            // Start the GraphsActivity (navigate to graph screen)
            startActivity(intent) // add here
        }
    }
}