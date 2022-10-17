package com.tamaki.workerapp.ui.screens.general.workerSignup.tabs

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
import com.tamaki.workerapp.data.viewModel.ExperienceType
import com.tamaki.workerapp.ui.screens.general.workerSignup.experienceSubcatagory.Formwork

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WorkerSignupExperience(
    experienceType: ExperienceType,
    ChangeExperienceType:(ExperienceType)->Unit,
    FormworkMap: Map<String, Boolean>,
    updateFormworkMap:(String)->Unit
) {
    Column() {
        Text(
            text = "Experience",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow {
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = if (experienceType == ExperienceType.Formwork)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            ChangeExperienceType(ExperienceType.Formwork)
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
                        text = "Formwork",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (experienceType == ExperienceType.Formwork)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = if (experienceType == ExperienceType.Machinery)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            ChangeExperienceType(ExperienceType.Machinery)
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
                        text = "Machinery",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (experienceType == ExperienceType.Machinery)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer

                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(
                            color = if (experienceType == ExperienceType.Rigging)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            ChangeExperienceType(ExperienceType.Rigging)
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
                        text = "Rigging",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (experienceType == ExperienceType.Rigging)MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer

                    )
                }
            }
        }
        AnimatedContent(
            targetState = experienceType,
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
                ExperienceType.Machinery-> {

                }
                ExperienceType.Formwork-> {
                    Formwork(
                        FormworkMap = FormworkMap,
                    updateFormworkMap = updateFormworkMap
                    )
                }
                ExperienceType.Rigging -> {

                }
                ExperienceType.Reinforcing -> {

                }
            }
        }
    }
}