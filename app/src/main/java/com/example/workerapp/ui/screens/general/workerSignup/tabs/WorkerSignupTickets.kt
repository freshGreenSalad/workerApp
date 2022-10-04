package com.example.workerapp.ui.screens.general.workerSignup

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.workerapp.data.viewModel.HighestClass
import com.example.workerapp.data.viewModel.TicketType
import com.example.workerapp.data.viewModel.TypeOfLicence
import com.example.workerapp.ui.screens.general.workerSignup.ticketSubcategories.DriversLicence

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
                            color = MaterialTheme.colorScheme.secondaryContainer,
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
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
                        text = "Lifts",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )


                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { ChangeTicketType(TicketType.Crane) },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    /*Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.crane),
                        contentDescription = "titles[1]",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.secondary
                    )*/
                    Text(
                        text = "Crane",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { ChangeTicketType(TicketType.DangerousSpaces) },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = "titles[1]",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Dangerous Spaces",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { ChangeTicketType(TicketType.Empty) },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = "titles[1]",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Empty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
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
                    Text(
                        text = "Empty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TicketType.Crane -> {
                    Text(
                        text = "Crane",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TicketType.DangerousSpaces -> {
                    Text(
                        text = "Dangerous Spaces",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TicketType.Lifts -> {
                    Text(
                        text = "Lifts",
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