package com.tedmob.challenge.applicationtofix.features.authentication

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.features.authentication.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isRegistering: Boolean = false,
        val registerError: String? = null,
        val redirectToLogin: Boolean = false,
    )

    private val registerUseCase: RegisterUseCase = RegisterUseCase(App.mainApi)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun register(
        firstName: String,
        lastName: String,
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(
                    isRegistering = true,
                )
                registerUseCase.execute(
                    RegisterUseCase.Params(firstName, lastName, username, password)
                )
            }.fold(
                onFailure = {
                    _state.value = _state.value.copy(
                        isRegistering = false,
                        registerError = it.message.orEmpty(),
                    )
                },
                onSuccess = {
                    _state.value = _state.value.copy(
                        isRegistering = false,
                        redirectToLogin = true,
                    )
                }
            )
        }
    }

    fun consumeRegisterState() {
        _state.value = _state.value.copy(
            registerError = null,
            redirectToLogin = false,
        )
    }
}