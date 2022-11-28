package com.tamaki.workerapp.data.viewModel

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.datastore.DatastoreInterface
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SigninViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStore: DatastoreInterface
) : ViewModel() {
    suspend fun login(authRequest: ProfileLoginAuthRequest) {
        val result = repository.login(authRequest)
        resultChannel.send(result)
    }

    var email = MutableStateFlow (TextFieldValue(""))
    var password = MutableStateFlow (TextFieldValue("") )

    private val resultChannel = Channel<authResult<Boolean?>>()

    val authResults = resultChannel.receiveAsFlow()

    private val _stateLogin = MutableStateFlow(LoginState())

    val stateLogin: StateFlow<LoginState>
        get() = _stateLogin

    init {
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(
                email,password
            ) { email,password ->
                LoginState(
                    email,
                    password
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateLogin.value = it
            }
        }
    }

    fun updatePasswordField(newPassword:TextFieldValue){
        password.value = newPassword
    }
    fun updateEmailField(newEmail:TextFieldValue){
        email.value = newEmail
    }

    fun tryToLoginToAccountWhenClickingOnButton(){
        viewModelScope.launch {
            login(ProfileLoginAuthRequest(email.value.text, password.value.text))
        }
    }

}

data class LoginState(
    val email: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue("")
)