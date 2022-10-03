package com.example.workerapp.ui.screens.general.profileCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/*
@Composable
fun specializedLicences(
    listoflicences: List<String>,
    addSpecilizedLicence: (String)-> Unit,
    removeSpecilisedLicence: (String)-> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DropDownMenuLicences(
            listoflicences,
            addSpecilizedLicence,
            removeSpecilisedLicence
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuLicences(
    listoflicences: List<String>,
    addSpecilizedLicence: (String)-> Unit,
    removeSpecilisedLicence: (String)-> Unit
) {
    val options = listOf(
        "Confined Spaces (AS 2865)",
        "EWP Licence",
        "Heights level one",
        "SizzorLift Licence",
        "knuckle boom licence"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row() {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    label = { Text("Label") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { addSpecilizedLicence(selectedOptionText)
                               },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                //    modifier = Modifier.size(SwitchDefaults.IconSize)
                )

            }
        }
        specilisedLicenceChipGroup(
            listoflicences,
            removeSpecilisedLicence
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun specilisedLicenceChipGroup(
    words: List<String>,
    removeSpecilisedLicence: (String)-> Unit
){
    val words = words
    ChipVerticalGrid(
        spacing = 7.dp,
        modifier = Modifier
            .padding(7.dp)
    ) {
        words.forEach { word ->
            InputChip(
                selected = true,
                label = {Text(text = word)},
                onClick = {removeSpecilisedLicence(word)},
                trailingIcon = {
                    Icon(
                    imageVector = Icons.Filled.Close ,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }
}

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentRow = 0
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                currentOrigin = currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}*/
