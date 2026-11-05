package ies.sequeros.pmdm.navegacion.principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.pmdm.navegacion.MainRoutes



import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrincipalViewModel : ViewModel() {
    //canal
    private val _navigationEvent = Channel< MainRoutes>()
     val navigationEvent = _navigationEvent.receiveAsFlow()


    fun exit() {

                //se envia el evento en el canal, a donde se quiere navegar
                viewModelScope.launch {
                    //se le indica que ha de navega al home
                    _navigationEvent.send(MainRoutes.Login)
                }

        }
    }


