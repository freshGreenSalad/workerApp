package com.tamaki.workerapp.ui.screens.general.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.destinations.SupervisorSignupScaffoldDestination
import com.tamaki.workerapp.destinations.WorkerSignupScaffoldDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.StandardOnPrimaryTextHeading
import com.tamaki.workerapp.ui.components.TextFieldWithKeyboardActions
import kotlinx.coroutines.launch

@ProfileCreationNavGraph(start = true)
@Destination
@Composable
fun Signup(
    viewModel: SignupViewModel,
    navigate: DestinationsNavigator
) {

    val state by viewModel.stateLogin.collectAsState()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            beginAccountCreation(result, navigate, state.isSupervisor, context)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            ButtonWithTickIfTrue(true, isSupervisorState = state.isSupervisor, text = "Supervisor", function = {(viewModel::updateIsSupervisor)(true)})
            Spacer(modifier = Modifier.height(15.dp))
            ButtonWithTickIfTrue(false,isSupervisorState = state.isSupervisor, text = "Worker", function = {(viewModel::updateIsSupervisor)(false)})
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldWithKeyboardActions("Email",viewModel::updateEmail,state.email)
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldWithKeyboardActions("Password",viewModel::updatePassword,state.password)
            Spacer(modifier = Modifier.height(15.dp))
            StandardButton("Sign up") { scope.launch { (viewModel::postEmailPasswordIsSupervisor)() }
            }
        }
    }
}

//worker
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ButtonWithTickIfTrue(isSupervisorBox:Boolean, isSupervisorState: Boolean, text:String, function:()->Unit) {
    AnimatedContent(
        targetState = isSupervisorState,
        transitionSpec = {
            fadeIn(animationSpec = tween(150, delayMillis = 150))
                .with(fadeOut(animationSpec = tween(150)))
        }
    ) { targetState ->
        when (targetState == !isSupervisorBox) {
            false -> {
                BoxWithTickAtEnd(function, text)
            }
            true -> {
                StandardButton(text, function)
            }
        }
    }
}

@Composable
private fun BoxWithTickAtEnd(
    function: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .width(280.dp)
            .clickable { function() }
            .background(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.primary
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            StandardOnPrimaryTextHeading(text = text)
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

fun beginAccountCreation(
    result: authResult<Unit>,
    navigate: DestinationsNavigator,
    isSupervisor: Boolean,
    context: Context
) {
    when (result) {
        is authResult.authorised -> navigate.navigate(if (isSupervisor) SupervisorSignupScaffoldDestination else WorkerSignupScaffoldDestination)
        is authResult.unauthorised -> {
            Log.d("signup", "unauthorised block")
            Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
        }
        is authResult.unknownError -> {
            Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
        }
        else -> {}
    }
}