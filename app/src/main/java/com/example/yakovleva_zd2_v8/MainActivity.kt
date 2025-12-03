package com.example.yakovleva_zd2_v8

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var rollsBtn: ImageButton
    private lateinit var cartBtn: ImageButton
    private lateinit var welcomeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var username = intent.getStringExtra("username")

        if (username.isNullOrEmpty()) {
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            username = prefs.getString("username", "Гость") ?: "Гость"
        }

        welcomeText = findViewById(R.id.welcome_text)
        welcomeText.text = "Привет, $username!"

        rollsBtn = findViewById(R.id.rolls_btn)
        cartBtn  = findViewById(R.id.cart_btn)

        rollsBtn.setOnClickListener {
            startActivity(Intent(this, AddRollActivity::class.java))
        }

        cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}