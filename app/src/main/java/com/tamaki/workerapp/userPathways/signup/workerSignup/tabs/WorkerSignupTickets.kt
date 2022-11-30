package com.tamaki.workerapp.userPathways.signup.workerSignup

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.*
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.userPathways.signup.workerSignup.ticketSubcategories.DriversLicence

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WorkerSignupTickets(
    viewModel: SignupViewModel
) {
    val viewState by viewModel.stateTickets.collectAsState()
    Column {
        StandardPrimaryTextHeading("Tickets")
        Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
        LazyRow {
            item {
                ticketTypeBoxClickable(TicketType.DriversLicence, {(viewModel::changeTicketType)(TicketType.DriversLicence)},viewState.ticketType )
            }
            item {
                ticketTypeBoxClickable(TicketType.Lifts, {(viewModel::changeTicketType)(TicketType.Lifts)},viewState.ticketType )
            }
        }
        AnimatedContent(
            targetState = viewState.ticketType,
            transitionSpec = {
                val direction = if (initialState.ordinal < targetState.ordinal)
                    AnimatedContentScope.SlideDirection.Left else AnimatedContentScope
                    .SlideDirection.Right

                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(500)
                ) with
                        slideOutOfContainer(
                            towards = direction,
                            animationSpec = tween(500)
                        ) using SizeTransform(
                    clip = false,
                    sizeAnimationSpec = { _, _ ->
                        tween(500, easing = EaseInOut)
                    }
                )
            }
        ) { targetState ->
            when (targetState) {
                TicketType.Empty -> {}
                TicketType.Crane -> {}
                TicketType.DangerousSpaces -> {}
                TicketType.Lifts -> {
                    Text(
                        text = "Unit Standards",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TicketType.DriversLicence -> {
                    DriversLicence(
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun ticketTypeBoxClickable(
    ticketType: TicketType,
    function: ()->Unit,
    currentTicketType: TicketType
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .background(
                color = if (currentTicketType == ticketType) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                function()
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Icon(
            imageVector = Icons.Outlined.Done,
            contentDescription = "titles[1]",
            modifier = Modifier.fillMaxSize(),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "Drivers Licence",
            style = MaterialTheme.typography.headlineSmall,
            color = if (currentTicketType == ticketType) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}