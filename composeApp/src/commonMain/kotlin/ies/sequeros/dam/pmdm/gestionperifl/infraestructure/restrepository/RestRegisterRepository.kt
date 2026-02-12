package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RestRegisterRepository(private val url: String, private val _client: HttpClient) : IAuthRepository {
    override suspend fun register(command: RegisterUserCommand) {
        val request = _client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(command)
        }
    }

    override suspend fun login(command: LoginUserCommand): User {
        TODO("Not yet implemented")
    }

    override suspend fun delete(command: RegisterUserCommand): User {
        TODO("Not yet implemented")
    }
}