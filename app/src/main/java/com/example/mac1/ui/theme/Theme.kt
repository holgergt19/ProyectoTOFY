package com.example.mac1.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Colores para el tema oscuro
private val DarkColorPalette = darkColors(
    primary = Color(0xFF6200EE), // Puedes modificar estos colores según tus necesidades
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6)
)

// Colores para el tema claro
private val LightColorPalette = lightColors(
    primary = Color(0xFF6200EE), // Modificar según los colores que desees
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6)
)

@Composable
fun Mac1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Usamos dinámicamente los colores en función del tema actual (oscuro o claro)
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    // Aplicamos el tema usando MaterialTheme
    MaterialTheme(
        colors = colors,
        typography = Typography, // Asegúrate de que tienes la configuración de tipografía
        content = content
    )
}
