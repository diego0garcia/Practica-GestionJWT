package ies.sequeros.dam.pmdm.gestionperifl.dominio.repository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.UserDto

interface IAuthRepository {
    suspend fun register(command: RegisterUserCommand) : UserDto
}