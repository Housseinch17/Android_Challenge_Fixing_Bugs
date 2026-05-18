package com.tedmob.challenge.applicationtofix.features.breeds

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.data.entity.BreedDetails
import com.tedmob.challenge.applicationtofix.features.breeds.domain.GetBreedDetailsUseCase
import com.tedmob.challenge.applicationtofix.features.home.MainRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreedDetailsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    @Immutable
    data class State(
        val id: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val data: BreedDetails? = null,
    )


    private val getBreedDetailsUseCase = GetBreedDetailsUseCase(App.mainApi)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        setUpId()
        getBreed()
    }

    private fun setUpId(){
            val id = stateHandle.toRoute<MainRoute.BreedDetails>().id
            _state.update { newState->
                newState.copy(
                    id = id
                )
            }
    }
    fun getBreed() {
        viewModelScope.launch {
            val id = _state.value.id
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