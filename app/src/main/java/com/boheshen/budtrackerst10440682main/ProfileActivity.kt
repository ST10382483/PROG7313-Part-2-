package com.boheshen.budtrackerst10440682main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var txtFullName: TextView
    private lateinit var txtNationality: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCellNumber: TextView
    private lateinit var btnEditDetails: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgProfile)
        txtFullName = findViewById(R.id.txtFullName)
        txtNationality = findViewById(R.id.txtNationality)
        txtEmail = findViewById(R.id.txtEmail)
        txtCellNumber = findViewById(R.id.txtCellNumber)
        btnEditDetails = findViewById(R.id.btnEditDetails)

        loadProfileDetails()

        btnEditDetails.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadProfileDetails()
    }