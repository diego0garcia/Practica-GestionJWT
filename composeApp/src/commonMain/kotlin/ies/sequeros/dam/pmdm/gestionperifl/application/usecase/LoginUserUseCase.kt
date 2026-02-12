package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository

class LoginUserUseCase(private val repository: IAuthRepository) {
    suspend fun invoke(command: LoginUserCommand): Result<User> {
        try {
            val user = repository.login(command)
            return Result.success(user)
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}