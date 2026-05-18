package com.tedmob.challenge.applicationtofix.features.breeds

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.data.entity.Breed
import com.tedmob.challenge.applicationtofix.data.entity.BreedDetails
import com.tedmob.challenge.applicationtofix.features.breeds.domain.GetBreedDetailsUseCase
import com.tedmob.challenge.applicationtofix.features.breeds.domain.GetBreedsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedDetailsViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: BreedDetails? = null,
    )


    private val getBreedDetailsUseCase = GetBreedDetailsUseCase(App.Companion.mainApi)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun getBreed(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            runCatching {
                getBreedDetailsUseCase.execute(GetBreedDetailsUseCase.Params(id))
            }.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        data = it,
                    )
                },
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message,
                    )
                },
            )
        }
    }
}