package ies.sequeros.pmdm.navigator3.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ies.sequeros.pmdm.navegacion.MainRoutes
import kotlinx.coroutines.flow.collect


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    //lambda
    onNavigateToHome: (MainRoutes) -> Unit
) {
    // Recolectamos el estado de forma segura
    val state by viewModel.uiState.collectAsState()
    //corrutina que se ejecuta cuando el componente aparece en pantalla
    //o el objeto que se le pasa como parámetro cambie
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { destination ->
            //la decisión de donde navegar la toma el viewModel, no la UI
            onNavigateToHome(destination)
        }
    }
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Correo electrónico") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))

        } else {

            Button(
                onClick = { viewModel.onLoginClicked() },
                enabled = state.isLoginEnabled // Usamos la validación del estado
            ) {
                Text("Iniciar Sesión")
            }
        }

        // error
        state.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}