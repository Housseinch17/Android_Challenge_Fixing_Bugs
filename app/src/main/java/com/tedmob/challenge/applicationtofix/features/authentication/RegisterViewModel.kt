package com.tedmob.challenge.applicationtofix.features.authentication

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import com.tedmob.challenge.applicationtofix.features.authentication.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isRegistering: Boolean = false,
        val registerError: String? = null,
        val redirectToLogin: Boolean = false,
        val firstName: String = "",
        val lastName: String = "",
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val missingError: String? = null
    )

    private val registerUseCase: RegisterUseCase = RegisterUseCase(App.mainApi)


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun register() {
        val state = _state.value

        val firstName = state.firstName
        val lastName = state.lastName
        val username = state.username
        val password = state.password
        val confirmPassword = state.confirmPassword

        when {
            firstName.isEmpty() -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "First Name is required"
                    )
                }
                return
            }

            lastName.isEmpty() -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "Last Name is required"
                    )
                }
                return
            }

            username.isEmpty() -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "Username is required"
                    )
                }
                return
            }

            password.isEmpty() -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "Password is required"
                    )
                }
                return
            }

            confirmPassword.isEmpty() -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "Confirm Password is required"
                    )
                }
                return
            }

            password != confirmPassword -> {
                _state.update { newState ->
                    newState.copy(
                        missingError = "Passwords do not match"
                    )
                }
                return
            }
        }

        Log.d("MyTag",
            "firstName: $firstName " +
                    "lastName: $lastName" +
                    "username: $username" +
                    "password: $password" +
                    "confirmPassword: $confirmPassword")

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

    fun updateFirstName(firstName: String) {
        _state.update { newState ->
            newState.copy(
                firstName = firstName
            )
        }
    }

    fun updateLastName(lastName: String) {
        _state.update { newState ->
            newState.copy(
                lastName = lastName
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

    fun updateConfirmPassword(confirmPassword: String) {
        _state.update { newState ->
            newState.copy(
                confirmPassword = confirmPassword
            )
        }
    }

    fun dismissMissingError(missingError: String?) {
        _state.update { newState ->
            newState.copy(
                missingError = missingError
            )
        }
    }
}