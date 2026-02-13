package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.LoginRespuesta
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*

class RestAuthRepository(private val url: String, private val _client: HttpClient, private val sesionManager: SesionManager) : IAuthRepository {
    override suspend fun register(command: RegisterUserCommand) {
        val request = _client.post(url + "register") {
            contentType(ContentType.Application.Json)
            setBody(command)
        }

    }

    override suspend fun login(command: LoginUserCommand): User {
        val request = _client.post(url + "login") {
            contentType(ContentType.Application.Json)
            setBody(command)
        }

        if(request.status.value == 401){
            throw Exception("Email o contraseña incorrectos")
        }

        if (!request.status.isSuccess()) {
            throw Exception("Error del servidor: ${request.status.value}")
        }

        val respuesta: LoginRespuesta = request.body()
        val tokenDatos = TokenJwt(respuesta.idToken)
        val tokenAccess = TokenJwt(respuesta.accessToken)
        val tokenRefresh = TokenJwt(respuesta.refreshToken)

        val user: User = User(
            id = tokenDatos.payload.userId ?: "",
            username = tokenDatos.payload.userName ?: "",
            email = tokenDatos.payload.userEmail ?: "",
            image = tokenDatos.payload.userImage,
            status = tokenDatos.payload.status,
        )

        sesionManager.iniciarSesion(user, tokenAccess.rawToken, tokenRefresh.rawToken, tokenDatos.rawToken)

        return user
    }

    override suspend fun delete(command: RegisterUserCommand): User {
        val request = _client.delete(url + "delete") {
            contentType(ContentType.Application.Json)
            setBody(command)
        }
        return request.body()
    }
}