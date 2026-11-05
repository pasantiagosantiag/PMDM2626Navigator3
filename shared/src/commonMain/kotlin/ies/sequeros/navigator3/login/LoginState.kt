package ies.sequeros.pmdm.navigator3.login
data class LoginState(
    val email: String = "admin@admin.com",
    val password: String = "1234",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {

    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}