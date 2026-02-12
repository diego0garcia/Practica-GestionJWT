package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.UserDto
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository

class RegisterUserUseCase(private val repository: IAuthRepository) {
    suspend fun invoke(command: RegisterUserCommand): Result<UserDto> {
        try {
            val user = repository.register(command)
            return Result.success(user)
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}