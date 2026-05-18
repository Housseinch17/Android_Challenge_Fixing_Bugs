package com.tedmob.challenge.applicationtofix.features.breeds.domain

import com.tedmob.challenge.applicationtofix.data.api.MainApi
import com.tedmob.challenge.applicationtofix.data.entity.Breed

class GetBreedsUseCase(
    private val api: MainApi,
) {

    suspend fun execute(): List<Breed> {
        return api.breeds()
            .data
            .map {
                Breed(
                    it.id,
                    it.attributes?.name.orEmpty(),
                    it.attributes?.name.orEmpty(),
                )
            }
    }
}