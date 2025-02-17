package com.example.mac1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class NextActivity : AppCompatActivity() {

    private var generatedCode: String? = null
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        username = intent.getStringExtra("username") ?: ""
        generatedCode = intent.getStringExtra("code") // Recuperar código si existe

        val usernameTextView = findViewById<TextView>(R.id.usernameTextView)
        val codeEditText = findViewById<EditText>(R.id.codeEditText)
        val joinButton = findViewById<Button>(R.id.joinButton)
        val generateButton = findViewById<Button>(R.id.generateButton)
        val backToRoomButton = findViewById<Button>(R.id.backToRoomButton)

        usernameTextView.text = "Usuario: $username"

        // Actualizar botones según el estado del código generado
        updateButtons(generateButton, backToRoomButton)

        // Unirse a una sala existente
        joinButton.setOnClickListener {
            val code = codeEditText.text.toString()
            if (code.isNotEmpty()) {
                navigateToPrivateRoom(code)
            } else {
                codeEditText.error = "Por favor, ingresa un código"
            }
        }

        // Generar un nuevo código y redirigir
        generateButton.setOnClickListener {
            generatedCode = (100000..999999).random().toString()
            navigateToPrivateRoom(generatedCode!!)
        }

        // Regresar a la sala generada previamente
        backToRoomButton.setOnClickListener {
            generatedCode?.let {
                navigateToPrivateRoom(it)
            }
        }
    }

    private fun updateButtons(generateButton: Button, backToRoomButton: Button) {
        if (generatedCode != null) {
            generateButton.isEnabled = false
            generateButton.text = "Código generado: $generatedCode"
            backToRoomButton.isEnabled = true
        } else {
            backToRoomButton.isEnabled = false
        }
    }

    private fun navigateToPrivateRoom(code: String) {
        val intent = Intent(this, PrivateRoomActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("code", code)
        startActivity(intent)
    }
}