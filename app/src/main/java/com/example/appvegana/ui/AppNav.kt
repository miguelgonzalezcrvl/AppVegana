package com.example.appvegana.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appvegana.data.FakeAuthRepository
import com.example.appvegana.ui.screens.LoginScreen
import com.example.appvegana.ui.screens.RegisterScreen
import com.example.appvegana.ui.screens.VerifyEmailScreen

@Composable
fun AppNav() {
    val nav = rememberNavController()
    // Un solo ViewModel compartido por las 3 pantallas.
    // Cuando exista el backend: cambiar FakeAuthRepository() por AuthRepositoryImpl(...)
    val vm: AuthViewModel = viewModel { AuthViewModel(FakeAuthRepository()) }
    val state by vm.state.collectAsState()

    NavHost(navController = nav, startDestination = "login") {

        composable("login") {
            LoginScreen(
                state = state,
                onLogin = { correo, pass ->
                    vm.login(correo, pass) {
                        nav.navigate("home") { popUpTo("login") { inclusive = true } }
                    }
                },
                onGoRegister = {
                    vm.limpiarMensajes()
                    nav.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                state = state,
                onRegister = { nombre, correo, pass ->
                    vm.registrar(nombre, correo, pass) { nav.navigate("verify") }
                },
                onGoLogin = {
                    vm.limpiarMensajes()
                    nav.popBackStack()
                }
            )
        }

        composable("verify") {
            VerifyEmailScreen(
                state = state,
                onVerify = { codigo ->
                    vm.verificar(codigo) {
                        nav.navigate("login") { popUpTo("login") { inclusive = true } }
                    }
                },
                onResend = { vm.reenviar() },
                onBack = {
                    vm.limpiarMensajes()
                    nav.popBackStack()
                }
            )
        }

        // Pantalla temporal: el resto del equipo la reemplaza por la lista de recetas.
        composable("home") {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sesión iniciada")
            }
        }
    }
}
