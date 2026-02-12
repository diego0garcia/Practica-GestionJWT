package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository

class RegisterUserUseCase(private val repository: IAuthRepository) {
    suspend fun invoke(command: RegisterUserCommand): Result<String> {
        try {
            val user = repository.register(command)
            return Result.success("Funciona")
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}