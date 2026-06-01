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