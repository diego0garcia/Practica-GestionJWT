package ies.sequeros.dam.pmdm.gestionperifl.di

import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository.RestLoginRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository.RestRegisterRepository
import org.koin.dsl.module

val domainModule = module {
    single<IAuthRepository> { RestRegisterRepository("http://localhost:8080/api/public/register", get()) }
    single<IAuthRepository> { RestLoginRepository("http://localhost:8080/api/public/login", get()) }
}