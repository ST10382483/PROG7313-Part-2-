package com.boheshen.budtrackerst10440682main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var edtFullName: EditText
    private lateinit var edtNationality: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtCellNumber: EditText

    private var selectedImageUri: Uri? = null
    private val imagePickerCode = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imgProfile = findViewById(R.id.imgProfile)
        edtFullName = findViewById(R.id.edtFullName)
        edtNationality = findViewById(R.id.edtNationality)
        edtEmail = findViewById(R.id.edtEmail)
        edtCellNumber = findViewById(R.id.edtCellNumber)

        loadExistingDetails()

        findViewById<Button>(R.id.btnSelectImage).setOnClickListener {
            selectImage()
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            saveProfile()
        }
    }

    private fun loadExistingDetails() {
        val prefs = getSharedPreferences("ProfileData", MODE_PRIVATE)

        edtFullName.setText(prefs.getString("fullName", ""))
        edtNationality.setText(prefs.getString("nationality", ""))
        edtEmail.setText(prefs.getString("email", ""))
        edtCellNumber.setText(prefs.getString("cellNumber", ""))

        val imageUri = prefs.getString("profileImageUri", null)

        if (!imageUri.isNullOrEmpty()) {
            imgProfile.setImageURI(Uri.parse(imageUri))
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        startActivityForResult(intent, imagePickerCode)
    }