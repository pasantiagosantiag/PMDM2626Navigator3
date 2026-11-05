package ies.sequeros.pmdm.navegacion

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoutes: NavKey  {
    @Serializable
    data object Home :  MainRoutes
    @Serializable
    data object Login :  MainRoutes
}