package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.LoginRespuesta
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class RestLoginRepository(private val url: String, private val _client: HttpClient) : IAuthRepository {
    override suspend fun register(command: RegisterUserCommand) {
        val request = _client.post("$url/register") {
            contentType(ContentType.Application.Json)
            setBody(command)
        }
        if (request.status.isSuccess()) {
            throw Exception("Error while registering user: ${request.status.description}")
        }
    }

    override suspend fun login(command: LoginUserCommand): User {
        val request = _client.post(url) {
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
        val token = TokenJwt(respuesta.idToken)

        val user: User = User(
            id = token.payload.userId ?: "",
            username = token.payload.userName ?: "",
            email = token.payload.userEmail ?: "",
            image = token.payload.userImage
        )

        return user
    }

    override suspend fun delete(command: RegisterUserCommand): User {
        TODO("Not yet implemented")
    }
}