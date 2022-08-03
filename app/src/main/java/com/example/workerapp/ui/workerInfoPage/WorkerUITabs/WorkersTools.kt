package com.example.workerapp.ui.workerInfoPage.WorkerUITabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.workerapp.R
import com.example.workerapp.ui.workerInfoPage.WorkerChipGroup
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun WorkersTools(
    paddingValues: PaddingValues
){
    LazyColumn(modifier = androidx.compose.ui.Modifier
        .padding(paddingValues)
        .fillMaxSize()){
        item {
            cardHoldingHorizontalPager()
        }
        item {
            WorkerChipGroup()
        }
        item {
            cardHoldingHorizontalPager()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun cardHoldingHorizontalPager(){
    Card(
        modifier = Modifier.padding(8.dp).height(350.dp)
    ) {
        val state = rememberPagerState()
        horizontalPager(
            state = state
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DotsIndicator(
                totalDots = 3,
                selectedIndex = state.currentPage,
                selectedColor = Color.LightGray,
                unSelectedColor = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun horizontalPager(
    state: PagerState
) {
    val slideImage = remember { mutableStateOf(R.drawable.eight) }
    HorizontalPager(count = 3, state = state) { page ->
        when (page) {
            0 -> {
                slideImage.value = R.drawable.one
            }

            1 -> {
                slideImage.value = R.drawable.five
            }

            2 -> {
                slideImage.value = R.drawable.four
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(slideImage.value),
                contentDescription = ""
            )
        }

    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}
