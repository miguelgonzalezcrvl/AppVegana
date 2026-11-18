package com.example.appvegana.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvegana.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val cargando: Boolean = false,
    val error: String? = null,
    val mensaje: String? = null,
    val correoPendiente: String = ""
)

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state

    private fun ejecutar(accion: suspend () -> Result<Unit>, onOk: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null, mensaje = null) }
            accion()
                .onSuccess {
                    _state.update { s -> s.copy(cargando = false) }
                    onOk()
                }
                .onFailure { e ->
                    _state.update { s ->
                        s.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }

    fun registrar(nombre: String, correo: String, pass: String, onOk: () -> Unit) =
        ejecutar({ repo.registrar(nombre, correo, pass) }) {
            _state.update { it.copy(correoPendiente = correo) }
            onOk()
        }

    fun verificar(codigo: String, onOk: () -> Unit) =
        ejecutar({ repo.verificar(_state.value.correoPendiente, codigo) }, onOk)

    fun reenviar() =
        ejecutar({ repo.reenviar(_state.value.correoPendiente) }) {
            _state.update { it.copy(mensaje = "Te enviamos un código nuevo") }
        }

    fun login(correo: String, pass: String, onOk: () -> Unit) =
        ejecutar({ repo.login(correo, pass) }, onOk)

    fun limpiarMensajes() {
        _state.update { it.copy(error = null, mensaje = null) }
    }
}
