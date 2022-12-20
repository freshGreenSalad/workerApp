package com.tamaki.workerapp.userPathways.signin.onetap.ui.dfgh

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.OnetapNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.tamaki.workerapp.destinations.AuthScreenDestination
import com.tamaki.workerapp.userPathways.signin.onetap.AuthViewModel
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.PROFILE_SCREEN
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.REVOKE_ACCESS
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.REVOKE_ACCESS_MESSAGE
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_OUT
import com.tamaki.workerapp.userPathways.signin.onetap.Response.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@OnetapNavGraph
@Destination
@Composable
fun ProfileScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ProfileTopBar(
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            ProfileContent(
                padding = padding,
                photoUrl = viewModel.photoUrl,
                displayName = viewModel.displayName
            )
        },
    )

    SignOut(
        viewModel,
        navigator
    )

    fun showSnackBar() = coroutineScope.launch {
        val result = snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    RevokeAccess(
        viewModel = viewModel,
        navigator = navigator,
        showSnackBar = { showSnackBar() }
    )
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun ProfileContent(
    padding: PaddingValues,
    photoUrl: String,
    displayName: String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(48.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier.clip(CircleShape).width(96.dp).height(96.dp)
        )
        Text(
            text = displayName,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ProfileTopBar(
    signOut: () -> Unit,
    revokeAccess: () -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }

    LargeTopAppBar(
        title = {
            Text(
                text = PROFILE_SCREEN
            )
        },
        actions = {
            IconButton(
                onClick = {
                    openMenu = !openMenu
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = {
                    openMenu = !openMenu
                }
            ) {
                DropdownMenuItem(
                    text = {Text(
                        text = SIGN_OUT
                    )},
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    }
                )
                DropdownMenuItem(
                    text = {Text(
                        text = REVOKE_ACCESS
                    )},
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    }
                )
            }
        }
    )
}

@Composable
fun RevokeAccess(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    showSnackBar: () -> Unit
) {
    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> revokeAccessResponse.data?.let { accessRevoked ->
            LaunchedEffect(accessRevoked) {
                if(accessRevoked){navigator.navigate(AuthScreenDestination)}
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(revokeAccessResponse.e)
            showSnackBar()
        }
        else -> {}
    }
}

@Composable
fun SignOut(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
    ) {
    when(val signOutResponse = viewModel.signOutResponse) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                if (signedOut){
                    navigator.navigate(AuthScreenDestination)
                }
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
        else -> {}
    }
}