package com.tamaki.workerapp.userPathways.signin.onetap

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.data.utility.Combine
import com.tamaki.workerapp.destinations.SupervisorHomeScaffoldDestination
import com.tamaki.workerapp.destinations.WorkerProfileDestination
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    val oneTapClient: SignInClient,
    private val repoprof: ProfileRepository,
    private val repository: RepositoryInterface,
): ViewModel() {

    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase

    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(Response.Success(false))
        private set

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Response.Loading
        oneTapSignInResponse = repo.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        oneTapSignInResponse = Response.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }

    val displayName get() = repoprof.displayName
    val photoUrl get() = repoprof.photoUrl

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))
        private set
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.Success(false))
        private set

    fun signOut() = viewModelScope.launch {
        signOutResponse = Response.Loading
        signOutResponse = repoprof.signOut()
    }

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Response.Loading
        revokeAccessResponse = repoprof.revokeAccess()
    }

    private suspend fun useProvidedLoginDetailsToTryLogin(authRequest: ProfileLoginAuthRequest) {
        val result = repository.login(authRequest)
        resultChannel.send(result)
    }

    var email = MutableStateFlow ("")
    var password = MutableStateFlow ("")
    private var scale = MutableStateFlow(Animatable(0f))

    private val resultChannel = Channel<authResult<Boolean?>>()

    private val authResults = resultChannel.receiveAsFlow()

    private val _stateLogin = MutableStateFlow(LoginState())

    val stateLogin: StateFlow<LoginState>
        get() = _stateLogin

    private val newEmail = MutableStateFlow("")
    private val newPassword = MutableStateFlow("")
    private val isSupervisor = MutableStateFlow(true)

    init {

        viewModelScope.launch {
            Combine().six(
                email,password,scale,newEmail,newPassword,isSupervisor
            ) { email,password,scale,newEmail,newPassword,isSupervisor ->
                LoginState(
                    email,
                    password,
                    scale,
                    newEmail,
                    newPassword,
                    isSupervisor
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateLogin.value = it
            }
        }
    }

    fun updatePasswordField(newPassword:String){
        password.value = newPassword
    }
    fun updateEmailField(newEmail:String){
        email.value = newEmail
    }

    fun tryToLoginToAccountWhenClickingOnButton(){
        viewModelScope.launch {
            useProvidedLoginDetailsToTryLogin(ProfileLoginAuthRequest(email.value, password.value))
        }
    }

    private fun checkLoginResultForSupervisorOrWorkerAccount(result: authResult<Boolean?>, navigator: DestinationsNavigator){
        when (result.data) {
            true -> {
                navigator.navigate(SupervisorHomeScaffoldDestination)
            }
            false -> {
                navigator.navigate(WorkerProfileDestination)
            }
            null -> {}
        }
    }
    private fun checkLoginDetailsAreCorrect(result: authResult<Boolean?>, context: Context) {
        when (result) {
            is authResult.unauthorised -> {
                Log.d("login", "unauthorised block")
                Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
            }
            is authResult.unknownError -> {
                Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    suspend fun returnAToastOrANavigationPathwayDependingOnLoginDetails(navigator: DestinationsNavigator, viewModel: AuthViewModel, context: Context){
        authResults.collect { result ->
            checkLoginResultForSupervisorOrWorkerAccount(result, navigator)
            checkLoginDetailsAreCorrect(result, context = context)
        }
    }

    fun updatePassword(newPassworda: String) { newPassword.value = newPassworda }
    fun updateEmail(newEmaila: String) { newEmail.value = newEmaila }
    fun updateIsSupervisor(newIsSupervisor: Boolean) { isSupervisor.value = newIsSupervisor }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val scale: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val newEmail: String = "",
    val newPassword: String = "",
    val isSupervisor:Boolean = true
)