package ies.sequeros.dam.pmdm.gestionperifl.modelo

data class User(
    val username: String,
    val email: String,
    val password: String,
    val image: String?
)