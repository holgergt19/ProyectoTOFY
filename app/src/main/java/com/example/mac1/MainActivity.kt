package com.example.mac1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val createButton = findViewById<Button>(R.id.createButton)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            if (username.isNotEmpty()) {
                val intent = Intent(this, NextActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                usernameEditText.error = "Por favor, ingresa un nombre de usuario"
            }
        }
    }
}