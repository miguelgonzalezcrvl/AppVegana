package com.example.appvegana.data

import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun registrar(nombre: String, correo: String, pass: String): Result<Unit>
    suspend fun verificar(correo: String, codigo: String): Result<Unit>
    suspend fun reenviar(correo: String): Result<Unit>
    suspend fun login(correo: String, pass: String): Result<Unit>
}

/**
 * Repository de prueba: simula las respuestas del backend.
 * Cuando exista el microservicio, se crea AuthRepositoryImpl con Retrofit
 * y se cambia solo la línea donde se instancia en AppNav.
 */
class FakeAuthRepository : AuthRepository {

    override suspend fun registrar(nombre: String, correo: String, pass: String): Result<Unit> {
        delay(600)
        return Result.success(Unit)
    }

    override suspend fun verificar(correo: String, codigo: String): Result<Unit> {
        delay(600)
        return if (codigo == "123456") Result.success(Unit)
        else Result.failure(Exception("Código inválido o vencido"))
    }

    override suspend fun reenviar(correo: String): Result<Unit> {
        delay(400)
        return Result.success(Unit)
    }

    override suspend fun login(correo: String, pass: String): Result<Unit> {
        delay(600)
        return Result.success(Unit)
    }
}
