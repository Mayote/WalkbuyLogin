package com.example.walkbuylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var signinBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById(R.id.MainLogInInBtn)
        signinBtn = findViewById(R.id.MainSignInBtn)

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginMenu::class.java)
            startActivity(intent)
        }

        signinBtn.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}