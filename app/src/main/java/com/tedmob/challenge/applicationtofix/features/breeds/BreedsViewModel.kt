package com.tedmob.challenge.applicationtofix.features.breeds

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.data.entity.Breed
import com.tedmob.challenge.applicationtofix.features.breeds.domain.GetBreedsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedsViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: List<Breed>? = null,
    )


    private val getBreedsUseCase = GetBreedsUseCase(App.Companion.mainApi)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun getBreeds() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            runCatching {
                getBreedsUseCase.execute()
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