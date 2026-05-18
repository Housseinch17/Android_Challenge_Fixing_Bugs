package com.tedmob.challenge.applicationtofix.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginResponseDTO(
    @SerialName("token") val token: String,
)