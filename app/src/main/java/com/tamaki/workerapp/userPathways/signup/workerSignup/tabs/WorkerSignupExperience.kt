package com.tamaki.workerapp.userPathways.signup.workerSignup.tabs

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
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.userPathways.signup.workerSignup.experienceSubcatagory.Formwork

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WorkerSignupExperience(
    experienceType: ExperienceType,
    ChangeExperienceType:(ExperienceType)->Unit,
    viewModel: SignupViewModel
) {
    Column() {
        StandardPrimaryTextHeading("Experience")
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow {
            item {
                boxWithSkillType(experienceType, ChangeExperienceType, ExperienceType.Formwork)
            }
            item {
                boxWithSkillType(experienceType, ChangeExperienceType,ExperienceType.Machinery)
            }
            item {
                boxWithSkillType(experienceType, ChangeExperienceType,ExperienceType.Rigging)
            }
            item {
                boxWithSkillType(experienceType, ChangeExperienceType,ExperienceType.Reinforcing)
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
                ExperienceType.Machinery-> {}
                ExperienceType.Formwork-> {Formwork(viewModel)}
                ExperienceType.Rigging -> {}
                ExperienceType.Reinforcing -> {}
            }
        }
    }
}

@Composable
private fun boxWithSkillType(
    CurrentExperienceType: ExperienceType,
    ChangeExperienceType: (ExperienceType) -> Unit,
    experienceType:ExperienceType
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .background(
                color = if (CurrentExperienceType == experienceType) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                ChangeExperienceType(experienceType)
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
            text = experienceType.name,
            style = MaterialTheme.typography.headlineSmall,
            color = if (CurrentExperienceType == experienceType) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}