package ies.sequeros.pmdm.navigator3.login
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import ies.sequeros.pmdm.navegacion.MainRoutes
import ies.sequeros.pmdm.navigator3.principal.PrincipalRoutes


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


class LoginViewModel : ViewModel() {
    //canal
    private val _navigationEvent = Channel< MainRoutes>()
     val navigationEvent = _navigationEvent.receiveAsFlow()
    //estado
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()


    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, errorMessage = null) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, errorMessage = null) }
    }

    // Acción de login
    fun onLoginClicked() {
        //se ejecuta en una corrutina, similar a un hilo
        viewModelScope.launch {
            // el estado es trabajando
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // se simula el trabajo
            val success = authenticate(_uiState.value.email, _uiState.value.password)

            if (success) {
                //se envia el evento en el canal, a donde se quiere navegar
                viewModelScope.launch {
                    //se le indica que ha de navega al home
                    _navigationEvent.send(MainRoutes.Home)
                }
            } else {
                // Si falla, desactivar la carga
                _uiState.update { it.copy(isLoading = false, errorMessage = "Credenciales incorrectas") }
            }
        }
    }
    fun reset(){
        _uiState.update { LoginState() }
    }
    private suspend fun authenticate(u: String, p: String): Boolean {
        kotlinx.coroutines.delay(1000) // Simulación
        return u == "admin@admin.com" && p == "1234"
    }
}