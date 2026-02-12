package ies.sequeros.dam.pmdm.gestionperifl.di

import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository.RestAuthRepository
import org.koin.dsl.module

val domainModule = module {
    single<IAuthRepository> { RestAuthRepository("http://localhost:8080/api/public/", get(), get()) }
}