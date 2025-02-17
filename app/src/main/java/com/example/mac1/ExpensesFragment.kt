package com.example.mac1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import com.example.mac1.R

class ExpensesFragment : Fragment() {

    private var totalFamiliar: Double = 1000.0 // Total familiar inicial
    private lateinit var totalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val rootView = inflater.inflate(R.layout.fragment_expenses, container, false)
        totalTextView = rootView.findViewById(R.id.totalTextView)

        val addExpenseButton = rootView.findViewById<Button>(R.id.addExpenseButton)
        addExpenseButton.setOnClickListener {
            // Agregar un gasto y actualizar el total
            totalFamiliar -= 50.0  // Gasto ejemplo
            totalTextView.text = "Total familiar: $${"%.2f".format(totalFamiliar)}"
        }

        return rootView
    }
}