package com.tamaki.workerapp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.viewModel.SupervisorSignupPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StandardButton(
    text: String,
    function: ()->Unit
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
                function
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun LogoImageBox(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(280.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.crane),
            contentDescription = "logo",
            modifier = Modifier.scale(2.5f)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithKeyboardActions(
    text: String,
    function: (String)->Unit,
    textFieldValue: String
){

    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )

    TextField(
        value = textFieldValue,
        onValueChange = {
            function(it)
        },
        placeholder = { Text(text) },
        singleLine = true,
        keyboardActions = keyboardActions
    )
}

@Composable
fun StandardTextHeading(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun LazyColumnOfOrganisedComposables(
    content: @Composable () -> Unit
) {
    LazyColumn() {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}

@Composable
fun ScreenShotImageHolder(uri: Uri) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .shadow(5.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun StepProgressBar(
    modifier: Modifier = Modifier,
    currentStep: Float
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            progress = currentStep,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun BottomBarSignUp(
    animatedProgress: Float,
    viewModel: SignupViewModel
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp, 10.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .padding(30.dp, 15.dp, 30.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            StepProgressBar(modifier = Modifier.fillMaxWidth(), currentStep = animatedProgress)
            Spacer(modifier = Modifier.height(5.dp))
            TwoButtons_nextScreen_PreviousScreen(scope, viewModel = viewModel)
        }
    }
}

@Composable
private fun TwoButtons_nextScreen_PreviousScreen(
    scope: CoroutineScope,
    viewModel: SignupViewModel
) {
    val supervisorState by viewModel.stateSupervisorScaffold.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StandardButton(text = "Back") { if (supervisorState.currentSupervisorStep == 0) { } else (viewModel::nextSupervisorScreen)(-1) }
        val text = if (supervisorState.supervisorSignupPoint == SupervisorSignupPoint.Map) "Done" else "Next"
        StandardButton(text = text) { if (supervisorState.supervisorSignupPoint == SupervisorSignupPoint.Map) { scope.launch { (viewModel::postSupervisorPersonalAndSite)() } } else (viewModel::nextSupervisorScreen)(1) }
    }
}

@Composable
fun FillMaxSizePaddingBox(
    it: PaddingValues,
    Composable: @Composable ()->Unit
) {
    Box(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        Composable()
    }
}