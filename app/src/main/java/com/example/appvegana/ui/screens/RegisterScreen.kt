package com.example.appvegana.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appvegana.ui.AuthUiState

@Composable
fun RegisterScreen(
    state: AuthUiState,
    onRegister: (nombre: String, correo: String, pass: String) -> Unit,
    onGoLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }

    val correoOk = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val passOk = pass.length >= 8
    val iguales = pass == pass2
    val formOk = nombre.isNotBlank() && correoOk && passOk && iguales

    AuthLayout(
        titulo = "Crea tu cuenta",
        subtitulo = "Guarda y comparte recetas veganas"
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it.trim() },
            label = { Text("Correo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = correo.isNotEmpty() && !correoOk,
            supportingText = { if (correo.isNotEmpty() && !correoOk) Text("Correo inválido") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = pass.isNotEmpty() && !passOk,
            supportingText = { if (pass.isNotEmpty() && !passOk) Text("Mínimo 8 caracteres") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = pass2,
            onValueChange = { pass2 = it },
            label = { Text("Repetir contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = pass2.isNotEmpty() && !iguales,
            supportingText = { if (pass2.isNotEmpty() && !iguales) Text("Las contraseñas no coinciden") },
            modifier = Modifier.fillMaxWidth()
        )

        AuthMensajes(state.error, state.mensaje)

        Button(
            onClick = { onRegister(nombre.trim(), correo, pass) },
            enabled = formOk && !state.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.cargando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text("Crear cuenta")
        }
        TextButton(onClick = onGoLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Ya tengo cuenta")
        }
    }
}