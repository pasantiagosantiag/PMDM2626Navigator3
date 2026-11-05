package ies.sequeros.navigator3


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import ies.sequeros.pmdm.navigator3.principal.Principal
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            // Registra todas las pantallas/rutas de tu sealed interface
            subclass(MainRoutes.Login::class, MainRoutes.Login.serializer())
            subclass(MainRoutes.Home::class, MainRoutes.Home.serializer())

        }
    }
}

@Composable
@Preview
fun App() {
    val backStack = rememberNavBackStack(navConfig, MainRoutes.Login)

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = { key ->
                    when (key) {
                        is MainRoutes.Login -> NavEntry(key) {
                            val viewModel = viewModel { LoginViewModel() }
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                LoginScreen(viewModel, {
                                    backStack.clear()
                                    //para que a la vuelta no se quede con los valores antiguos
                                    viewModel.reset()
                                    backStack.add(MainRoutes.Home)

                                })
                            }
                        }
                        is MainRoutes.Home -> NavEntry(key) {
                            val viewModel = viewModel { PrincipalViewModel() }
                            Principal(viewModel = viewModel, onExit = {
                                backStack.clear()
                                backStack.add(it)
                            })
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