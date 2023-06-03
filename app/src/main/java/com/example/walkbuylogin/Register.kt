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

class Register : AppCompatActivity() {

    private lateinit var userInput: TextView
    private lateinit var emailInput: TextView
    private lateinit var passInput: TextView
    private lateinit var conPassInput: TextView
    private lateinit var registerBtn: Button

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUp()
    }

    fun signIn(){
        val user = userInput.text
        val email = emailInput.text
        val pass = passInput.text
        val pass2 = conPassInput.text

        if(user.isNotEmpty() &&
            email.isNotEmpty() &&
            pass.isNotEmpty() &&
            pass2.isNotEmpty()){

            if(pass.toString() == pass2.toString()){
                auth.createUserWithEmailAndPassword(email.toString(),pass2.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful) {
                            Toast.makeText(baseContext, "Registro Exitoso", Toast.LENGTH_SHORT,
                            ).show()
                            finish()
                        }
                        else{
                            Toast.makeText(baseContext, "Registro Fallido.", Toast.LENGTH_SHORT,
                            ).show()
                            finish()
                        }
                    }
            }
            else{
                Toast.makeText(baseContext, "Rellene todo los campos", Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
    fun setUp(){
        userInput = findViewById(R.id.SignInUserInput)
        emailInput = findViewById(R.id.SignInEmailInput)
        passInput = findViewById(R.id.SignInPassInput)
        conPassInput = findViewById(R.id.SignInConfirmPassInput)
        registerBtn = findViewById(R.id.SignInRegisterBtn)

        auth = Firebase.auth

        registerBtn.setOnClickListener {
            signIn()
        }

    }
}