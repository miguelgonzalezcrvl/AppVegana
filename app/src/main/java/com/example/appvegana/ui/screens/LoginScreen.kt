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
fun LoginScreen(
    state: AuthUiState,
    onLogin: (correo: String, pass: String) -> Unit,
    onGoRegister: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val correoOk = Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val formOk = correoOk && pass.isNotEmpty()

    AuthLayout(titulo = "Inicia sesión") {
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
            modifier = Modifier.fillMaxWidth()
        )

        AuthMensajes(state.error, state.mensaje)

        Button(
            onClick = { onLogin(correo, pass) },
            enabled = formOk && !state.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.cargando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text("Ingresar")
        }
        TextButton(onClick = onGoRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Crear cuenta")
        }
    }
}
