package com.example.mac1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mac1.fragments.CalendarFragment
import com.google.firebase.database.*

class PrivateRoomActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var totalEarnings: Double = 1000.0 // Total inicial de ganancias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_room)

        // Obtener datos del intent
        val username = intent.getStringExtra("username")
        val code = intent.getStringExtra("code")

        // Configurar vistas
        val codeTextView = findViewById<TextView>(R.id.codeTextView)
        val usersTextView = findViewById<TextView>(R.id.usersTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        val addExpenseButton = findViewById<Button>(R.id.addExpenseButton)

        // Mostrar el código de la sala
        codeTextView.text = "Código de sala: $code"

        // Configurar Firebase
        database = FirebaseDatabase.getInstance().reference.child("rooms").child(code!!)

        // Añadir usuario a la sala
        database.child("users").child(username!!).setValue(true)

        // Escuchar cambios en los usuarios de la sala
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.map { it.key }.joinToString("\n")
                usersTextView.text = "Usuarios en la sala:\n$users"
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores
            }
        })

        // Configurar el botón de regreso
        backButton.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("code", code)
            startActivity(intent)
        }

        // Configurar el botón de agregar gastos
        addExpenseButton.setOnClickListener {
            showAddExpenseDialog()
        }

        // Cargar el fragmento de calendario
        loadFragment(CalendarFragment(), R.id.calendarSection)
    }

    // Método para mostrar la ventana de diálogo de agregar gastos
    private fun showAddExpenseDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_expense, null)
        val expenseNameInput = dialogView.findViewById<EditText>(R.id.expenseNameInput)
        val expenseAmountInput = dialogView.findViewById<EditText>(R.id.expenseAmountInput)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Gasto")
            .setView(dialogView)
            .setPositiveButton("Agregar") { _, _ ->
                val expenseName = expenseNameInput.text.toString()
                val expenseAmount = expenseAmountInput.text.toString().toDoubleOrNull() ?: 0.0

                if (expenseName.isNotEmpty() && expenseAmount > 0) {
                    // Restar el gasto al total de ganancias
                    subtractExpenseFromTotal(expenseAmount)
                    Toast.makeText(this, "Gasto agregado: $expenseName - $expenseAmount", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Ingresa un nombre y un monto válido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    // Método para restar el gasto al total de ganancias
    private fun subtractExpenseFromTotal(expenseAmount: Double) {
        totalEarnings -= expenseAmount
        updateTotalEarningsView()
    }

    // Método para actualizar la vista del total de ganancias
    private fun updateTotalEarningsView() {
        val totalTextView = findViewById<TextView>(R.id.totalTextView)
        totalTextView.text = "Total de ganancias: $${"%.2f".format(totalEarnings)}"
    }

    // Método para cargar fragmentos
    private fun loadFragment(fragment: Fragment, containerId: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.commit()
    }
}