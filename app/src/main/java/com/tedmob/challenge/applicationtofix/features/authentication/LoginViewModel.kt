package com.tedmob.challenge.applicationtofix.features.authentication

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.features.authentication.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isLoggingIn: Boolean = false,
        val loginError: String? = null,
        val redirectToMain: Boolean = false,
    )

    private val loginUseCase: LoginUseCase = LoginUseCase(App.mainApi, App.prefs)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun login(username: String, password: String) {
        viewModelScope.launch {
            runCatching {
                _state.value = _state.value.copy(
                    isLoggingIn = true,
                )
                loginUseCase.execute(LoginUseCase.Params(username, password))
            }.fold(
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoggingIn = false,
                        loginError = it.message.orEmpty(),
                    )
                },
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoggingIn = false,
                        redirectToMain = true,
                    )
                }
            )
        }
    }

    fun consumeLoginState() {
        _state.value = _state.value.copy(
            loginError = null,
            redirectToMain = false,
        )
    }
}