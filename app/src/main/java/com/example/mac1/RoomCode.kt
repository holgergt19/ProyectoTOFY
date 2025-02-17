package com.example.mac1  // Asegúrate de que esté en el paquete correcto

data class RoomCode(
    val code: String? = null,    // El código único de la sala
    val isActive: Boolean = false  // Indica si la sala está activa (privada)
)
