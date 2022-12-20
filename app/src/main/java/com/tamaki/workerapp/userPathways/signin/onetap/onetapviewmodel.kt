package com.tamaki.workerapp.userPathways.signin.onetap

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.destinations.SupervisorHomeScaffoldDestination
import com.tamaki.workerapp.destinations.WorkerProfileDestination
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.*
import com.tamaki.workerapp.userPathways.signin.onetap.utilities.MetaDataReader
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
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader,
): ViewModel() {
    //_______
    private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())

    val videoItems = videoUris.map { uris ->
        uris.map { uri ->
            VideoItem(
                contentUri = uri,
                mediaItem = MediaItem.fromUri(uri),
                name = metaDataReader.getMetaDataFromUri(uri)?.fileName ?: "No name"
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
    }

    fun addVideoUri(uri: Uri) {
        savedStateHandle["videoUris"] = videoUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(uri: Uri) {
        player.setMediaItem(
            videoItems.value.find { it.contentUri == uri }?.mediaItem ?: return
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
    //_____________

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

    init {


        viewModelScope.launch {
            combine(
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

    suspend fun changeScaleOfSplashScreenImage(){
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
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val scale: Animatable<Float, AnimationVector1D> = Animatable(0f)
)

data class VideoItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String
)