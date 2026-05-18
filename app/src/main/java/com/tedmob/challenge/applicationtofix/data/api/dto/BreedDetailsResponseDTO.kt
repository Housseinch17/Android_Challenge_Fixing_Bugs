package com.tedmob.challenge.applicationtofix.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BreedDetailsResponseDTO(
    @SerialName("id") val id: String,
    @SerialName("attributes") val attributes: Attributes?,
) {

    @Serializable
    class Attributes(
        @SerialName("name") val name: String?,
        @SerialName("description") val description: String?,
        @SerialName("life") val life: MinMax?,
        @SerialName("hypoallergenic") val hypoallergenic: Boolean?,
    )

    @Serializable
    class MinMax(
        @SerialName("min") val min: Double?,
        @SerialName("max") val max: Double?,
    )
}