package ies.sequeros.pmdm.navigator3.principal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoorBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration

import ies.sequeros.pmdm.navegacion.MainRoutes
import ies.sequeros.pmdm.navegacion.principal.PrincipalViewModel
import ies.sequeros.pmdm.navigator3.login.LoginScreen
import ies.sequeros.pmdm.navigator3.login.LoginViewModel
import ies.sequeros.pmdm.navigator3.principal.screens.Home
import ies.sequeros.pmdm.navigator3.principal.screens.Profile
import ies.sequeros.pmdm.navigator3.principal.screens.Settings
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            // Registra todas las pantallas/rutas de tu sealed interface
            subclass(PrincipalRoutes.Home::class, PrincipalRoutes.Home.serializer())
            subclass(PrincipalRoutes.Profile::class, PrincipalRoutes.Profile.serializer())
            subclass(PrincipalRoutes.Settings::class, PrincipalRoutes.Settings.serializer())

        }
    }
}
@Composable
fun Principal(
    modifier: Modifier = Modifier,
    onExit: (MainRoutes) -> Unit,
    viewModel: PrincipalViewModel
) {
    val backStack = rememberNavBackStack(navConfig, PrincipalRoutes.Home)


    Row(modifier = Modifier.fillMaxSize()) {
        // Rail izquierdo
        NavigationRail {

            NavigationRailItem(
                //si el destino actual es el de home se marca como seleccinado
                selected = backStack.lastOrNull()== PrincipalRoutes.Home,
                onClick = {
backStack.removeLastOrNull()
                    backStack.add(PrincipalRoutes.Home)
                },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") }
            )
            NavigationRailItem(
                selected = backStack.lastOrNull()== PrincipalRoutes.Profile,
                onClick = {
                    backStack.removeLastOrNull()
                    backStack.add(PrincipalRoutes.Profile("userid","usuario"))
                },
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                label = { Text("Perfil") }
            )
            NavigationRailItem(
                selected = backStack.lastOrNull()== PrincipalRoutes.Settings,
                onClick = {
                    backStack.removeLastOrNull()
                    backStack.add(PrincipalRoutes.Settings)
                },
                icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                label = { Text("Ajustes") }
            )
            NavigationRailItem(
                selected = false, //backStack.lastOrNull()== PrincipalRoutes.Settings,
                onClick = {
                    onExit(MainRoutes.Login)
                },
                icon = { Icon(Icons.Default.DoorBack, contentDescription = "Salir") },
                label = { Text("Salir") }
            )


        }
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = { key ->
                    when (key) {
                        is PrincipalRoutes.Home -> NavEntry(key) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Home()
                            }

                        }
                        is PrincipalRoutes.Profile -> NavEntry(key) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                Profile(id= key.userId)

                            }
                        }
                        is PrincipalRoutes.Settings -> NavEntry(key) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Settings()
                            }
                        }



                        else -> {
                            error("Unknown route: $key")
                        }
                    }
                }
            )

        }

    }
}