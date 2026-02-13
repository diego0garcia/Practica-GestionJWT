package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserStatus {
    @SerialName("pending")
    PENDING,
    @SerialName("active")
    ACTIVE,
    @SerialName("inactive")
    INACTIVE,
    @SerialName("suspended")
    SUSPENDED
}