package com.example.appvegana.ui.screens

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
import androidx.compose.ui.unit.dp
import com.example.appvegana.ui.AuthUiState

@Composable
fun VerifyEmailScreen(
    state: AuthUiState,
    onVerify: (codigo: String) -> Unit,
    onResend: () -> Unit,
    onBack: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }

    AuthLayout(
        titulo = "Confirma tu correo",
        subtitulo = "Enviamos un código de 6 dígitos a ${state.correoPendiente}"
    ) {
        OutlinedTextField(
            value = codigo,
            onValueChange = { if (it.length <= 6 && it.all(Char::isDigit)) codigo = it },
            label = { Text("Código") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        AuthMensajes(state.error, state.mensaje)

        Button(
            onClick = { onVerify(codigo) },
            enabled = codigo.length == 6 && !state.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.cargando) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text("Verificar")
        }
        TextButton(onClick = onResend, enabled = !state.cargando, modifier = Modifier.fillMaxWidth()) {
            Text("Reenviar código")
        }
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
