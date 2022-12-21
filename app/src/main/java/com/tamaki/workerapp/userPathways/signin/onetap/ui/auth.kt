package com.tamaki.workerapp.userPathways.signin.onetap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
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
    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Loading -> ProgressBar()
        is Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                if (signedIn) {
                    navigator.navigate(ProfileScreenDestination)
                }
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
    oneTapSignIn: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(50.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable {
                oneTapSignIn()
            },
        contentAlignment = Alignment.Center,


        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
        Image(
            painter = painterResource(
                id = R.drawable.googlelogo
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = SIGN_IN_WITH_GOOGLE,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    }
}


@Composable
fun OneTapSignIn(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
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
fun asdf() {

}