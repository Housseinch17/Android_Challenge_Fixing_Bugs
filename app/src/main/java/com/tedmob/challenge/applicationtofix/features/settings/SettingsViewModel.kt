package com.tedmob.challenge.applicationtofix.features.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmob.challenge.applicationtofix.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    //...
) : ViewModel() {

    @Immutable
    data class State(
        val isLoggingOut: Boolean = false,
        val logOutError: String? = null,
        val restartApp: Boolean = false,
    )


    private val logoutUseCase = LogoutUseCase(App.prefs, App.mainApi)

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    fun logout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingOut = true)
            runCatching {
                logoutUseCase.execute()
            }.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoggingOut = false,
                        restartApp = true,
                    )
                },
                onFailure = {
                    _state.value = _state.value.copy(
                        isLoggingOut = false,
                        logOutError = it.message,
                    )
                },
            )
        }
    }

    fun consumeLogoutState() {
        _state.value = _state.value.copy(
            isLoggingOut = false,
            logOutError = null,
            restartApp = false,
        )
    }
}