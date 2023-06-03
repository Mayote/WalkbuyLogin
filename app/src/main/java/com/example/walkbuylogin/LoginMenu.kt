package com.example.walkbuylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginMenu : AppCompatActivity() {
    private lateinit var emailInput: TextView
    private lateinit var passwordInput: TextView
    private lateinit var logInBtn: Button

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUp()
    }
    fun signIn(){
        val email = emailInput.text
        val password = passwordInput.text
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email.toString(),password.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.", Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        else
            Toast.makeText(baseContext, "Rellenar todos los campos", Toast.LENGTH_SHORT,
            ).show()
    }


    private fun setUp(){
        emailInput = findViewById(R.id.logInEmailInput)
        passwordInput = findViewById(R.id.loginPasswordInput)
        logInBtn = findViewById(R.id.LoginBtn)


        auth = Firebase.auth

        logInBtn.setOnClickListener{
            signIn()
        }

    }
}