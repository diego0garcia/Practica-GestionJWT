package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.UserDto
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RestRegisterRepository(private val url: String, private val _client: HttpClient) : IAuthRepository {
    override suspend fun register(command: RegisterUserCommand): UserDto {
        val request = _client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(command)
        }
        return request.body<UserDto>()
    }
}