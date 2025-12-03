package com.example.yakovleva_zd2_v8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var prefs: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = findViewById(R.id.name_edittext)
        password = findViewById(R.id.password_edittext)
        login = findViewById(R.id.login_btn)

        prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        gson = Gson()

        autoFill()

        login.setOnClickListener {
            val username = name.text.toString()
            val pass = password.text.toString()
            if (username.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            else if (password.text.length < 8)
                Toast.makeText(this, "Поле пароль должно содержать не менее 8 символов", Toast.LENGTH_SHORT).show()
            else {
                val userJson = """{
                "name": "$username",
                "password": "$pass"}""".trimIndent()

                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("user_data", userJson)
                    apply()
                }

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            }
        }
    }

    //автозаполнение эдиттекстов
    private fun autoFill(){
        val userJson = prefs.getString("user_data", null)

        if (userJson != null){
            val savedUser = gson.fromJson(userJson, User::class.java)
            name.setText(savedUser.name)
            password.setText(savedUser.password)
        }
    }
}