package com.tedmob.challenge.applicationtofix.features.authentication

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.features.authentication.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isLoggingIn: Boolean = false,
        val loginError: String? = null,
        val redirectToMain: Boolean = false,
        val username: String = "",
        val password: String = "",
        val missingError: String? = null
    )

    private val loginUseCase: LoginUseCase = LoginUseCase(App.mainApi, App.prefs)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun login() {
        viewModelScope.launch {
            val username = _state.value.username
            val password = _state.value.password
            when{
                username.isEmpty() && password.isEmpty() -> {
                    _state.update { newState->
                        newState.copy(
                            missingError = "Username and Password are required"
                        )
                    }
                    return@launch
                }
                username.isEmpty() -> {
                    _state.update { newState->
                        newState.copy(
                            missingError = "Username is required"
                        )
                    }
                    return@launch
                }
                password.isEmpty() -> {
                    _state.update { newState->
                        newState.copy(
                            missingError = "Password is required"
                        )
                    }
                    return@launch
                }
                else -> {}
            }
            runCatching {
                _state.update { newState ->
                    newState.copy(
                        isLoggingIn = true
                    )
                }
                loginUseCase.execute(LoginUseCase.Params(username, password))
            }.fold(
                onFailure = {
                    _state.update { newState ->
                        newState.copy(
                            isLoggingIn = false,
                            loginError = it.message.orEmpty(),
                        )
                    }
                },
                onSuccess = {
                    _state.update { newState ->
                        newState.copy(
                            isLoggingIn = false,
                            redirectToMain = true,
                        )
                    }
                }
            )
        }
    }

    fun consumeLoginState() {
        _state.update { newState ->
            newState.copy(
                loginError = null,
                redirectToMain = false,
            )
        }
    }

    fun updateUsername(username: String) {
        _state.update { newState ->
            newState.copy(
                username = username
            )
        }
    }

    fun updatePassword(password: String) {
        _state.update { newState ->
            newState.copy(
                password = password
            )
        }
    }

    fun dismissMissingError(missingError: String?){
        _state.update { newState->
            newState.copy(
                missingError = missingError
            )
        }
    }
}