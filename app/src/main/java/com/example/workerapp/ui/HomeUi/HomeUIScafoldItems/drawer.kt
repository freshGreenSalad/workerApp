package com.example.workerapp.ui.HomeUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workerapp.ui.destinations.MainHolderComposableDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun MainDrawer(
    navigator: DestinationsNavigator
){
   Column(
       modifier = Modifier.fillMaxWidth()
   ) {
       Text(text = "Menu",
           modifier = Modifier.padding(10.dp),
           style = MaterialTheme.typography.bodyMedium
       )
       Divider(modifier = Modifier
           .fillMaxWidth()
           .height(1.dp))
       Box (
           modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp)
               .height(40.dp)
               .clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.secondary)
               .clickable {
                   navigator.navigate(MainHolderComposableDestination)
               }
               ){
           Text(text = "Home",
               color = MaterialTheme.colorScheme.onSecondary,
               fontSize = 22.sp,
               modifier = Modifier.fillMaxSize().padding(4.dp),
               style = MaterialTheme.typography.bodyMedium,

           )
       }

   }
}
