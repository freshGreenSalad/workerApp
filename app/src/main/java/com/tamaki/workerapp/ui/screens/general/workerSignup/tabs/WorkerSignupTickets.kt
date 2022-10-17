package com.tamaki.workerapp.ui.screens.general.workerSignup

import com.tamaki.workerapp.R
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.TicketType
import com.tamaki.workerapp.data.viewModel.TypeOfLicence
import com.tamaki.workerapp.ui.screens.general.workerSignup.ticketSubcategories.DriversLicence

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WorkerSignupTickets(
    ticketType: TicketType,
    ChangeTicketType: (TicketType) -> Unit,
    LicenceType: TypeOfLicence,
    ChangeLicenceType: (TypeOfLicence) -> Unit,
    licenceMap: Map<String, Boolean>,
    updateLicenceEntry: (String) -> Unit,
    highestClass: HighestClass,
    UpdateHighestClass: (HighestClass) -> Unit
) {
    Column {
        Text(
            text = "Tickets",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        LazyRow {
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = if (ticketType == TicketType.DriversLicence)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            ChangeTicketType(TicketType.DriversLicence)
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
                        color = if (ticketType == TicketType.DriversLicence)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = if (ticketType == TicketType.Lifts)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { ChangeTicketType(TicketType.Lifts) },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = "titles[1]",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Unit Standards",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (ticketType == TicketType.Lifts)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
                    )


                }
            }
        }

        AnimatedContent(
            targetState = ticketType,
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
                TicketType.Empty -> {
                    /*Text(
                        text = "Empty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )*/
                }
                TicketType.Crane -> {

                }
                TicketType.DangerousSpaces -> {
                    /*Text(
                        text = "Dangerous Spaces",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )*/
                }
                TicketType.Lifts -> {
                    /*Text(
                        text = "Lifts",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )*/
                    Text(
                        text = "Unit Standards",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TicketType.DriversLicence -> {
                    DriversLicence(
                        LicenceType = LicenceType,
                        ChangeLicenceType = ChangeLicenceType,
                        licenceMap = licenceMap,
                        updateLicenceEntry = updateLicenceEntry,
                        highestClass = highestClass,
                        UpdateHighestClass = UpdateHighestClass
                    )
                }
            }
        }
    }
}