package com.tedmob.challenge.applicationtofix.features.breeds.domain

import android.icu.text.DecimalFormat
import com.tedmob.challenge.applicationtofix.data.api.MainApi
import com.tedmob.challenge.applicationtofix.data.entity.BreedDetails

class GetBreedDetailsUseCase(
    private val api: MainApi,
) {

    class Params(
        val id: String,
    )

    private val formatter by lazy { DecimalFormat("#,##0") }


    suspend fun execute(params: Params): BreedDetails {
        return api.breed(params.id)
            .let {
                val attributes = it.attributes

                BreedDetails(
                    it.id,
                    attributes?.name.orEmpty(),
                    attributes?.description.orEmpty(),
                    attributes?.life?.min?.let { formatter.format(it) }.orEmpty(),
                    attributes?.life?.max?.let { formatter.format(it) }.orEmpty(),
                    attributes?.hypoallergenic == true,
                )
            }
    }
}