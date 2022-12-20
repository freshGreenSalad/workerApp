package com.tamaki.workerapp.userPathways.signin.onetap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.tamaki.workerapp.userPathways.signin.onetap.AuthViewModel
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.AUTH_SCREEN
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_IN_WITH_GOOGLE
import com.tamaki.workerapp.userPathways.signin.onetap.Response.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.ui.Alignment
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.navgraphs.SigninNavGraph
import com.tamaki.workerapp.destinations.ProfileScreenDestination
import com.tamaki.workerapp.userPathways.signin.onetap.ui.dfgh.ProgressBar


@Composable
fun SignInWithGoogle(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    when(val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Loading -> ProgressBar()
        is Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if(signedIn){navigator.navigate(ProfileScreenDestination)}
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(signInWithGoogleResponse.e)
        }
        else -> {}
    }
}

@Composable
fun signInWithGoogle(
    padding: PaddingValues,
    oneTapSignIn: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier.padding(bottom = 48.dp),
            shape = RoundedCornerShape(6.dp),
            onClick = oneTapSignIn
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.googlelogo
                ),
                contentDescription = null
            )
            Text(
                text = SIGN_IN_WITH_GOOGLE,
                modifier = Modifier.padding(6.dp),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun OneTapSignIn(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when(val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Loading -> ProgressBar()
        is Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(oneTapSignInResponse.e)
        }
        else -> {}
    }
}

@SigninNavGraph(start = true)
@Destination
@Composable
fun asdf(){

}