package com.tamaki.workerapp.data.viewModel

import android.content.Context
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.destinations.MainHolderComposableDestination
import com.tamaki.workerapp.destinations.WorkerProfileDestination
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
) : ViewModel() {

    private suspend fun useProvidedLoginDetailsToTryLogin(authRequest: ProfileLoginAuthRequest) {
        val result = repository.login(authRequest)
        resultChannel.send(result)
    }

    var email = MutableStateFlow (TextFieldValue(""))
    var password = MutableStateFlow (TextFieldValue(""))
    private var scale = MutableStateFlow(Animatable(0f))

    private val resultChannel = Channel<authResult<Boolean?>>()

    private val authResults = resultChannel.receiveAsFlow()

    private val _stateLogin = MutableStateFlow(LoginState())

    val stateLogin: StateFlow<LoginState>
        get() = _stateLogin

    init {
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(
                email,password,scale
            ) { email,password,scale ->
                LoginState(
                    email,
                    password,
                    scale
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
            useProvidedLoginDetailsToTryLogin(ProfileLoginAuthRequest(email.value.text, password.value.text))
        }
    }

    fun changeScaleOfSplashScreenImage(){
        viewModelScope.launch {
            scale.value.animateTo(
                targetValue = 3f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        OvershootInterpolator(2f).getInterpolation(it)
                    }
                )
            )
        }
    }

    private fun checkLoginResultForSupervisorOrWorkerAccount(result: authResult<Boolean?>, navigator: DestinationsNavigator){
        when (result.data) {
            true -> {
                navigator.navigate(MainHolderComposableDestination)
            }
            false -> {
                navigator.navigate(WorkerProfileDestination)
            }
            null -> {}
        }
    }
    private fun checkLoginDetailsAreCorrect(result: authResult<Boolean?>, context:Context) {
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

    suspend fun returnAToastOrANavigationPathwayDependingOnLoginDetails(navigator:DestinationsNavigator, viewModel: SigninViewModel, context: Context){
        viewModel.authResults.collect { result ->
            (viewModel::checkLoginResultForSupervisorOrWorkerAccount)(result, navigator)
            checkLoginDetailsAreCorrect(result, context = context)
        }
    }
}

data class LoginState(
    val email: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val scale: Animatable<Float, AnimationVector1D> = Animatable(0f)
)