package com.tamaki.workerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
fun LineDevider() {
    Divider(
        modifier = Modifier
            .padding(end = 30.dp)
            .fillMaxWidth()
            .height(2.dp)
    )
}

@Composable
fun textFormatBetweenSections(text:String) {
    TextHeadingHomePage(text)
    LineDevider()
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ComposableThatSetsInitialScreenParameters(
    composable: @Composable ()-> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        composable()
    }
}