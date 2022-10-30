package com.tamaki.workerapp.ui.workerInfoPage.workerUITabs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.TypeOfLicence
import kotlin.reflect.KSuspendFunction1

@Composable
fun WorkerPhotoTab(
    paddingValues: PaddingValues,
    worker: WorkerProfile,
    getWorkerDriverLicenceviewModel: KSuspendFunction1<String, DriversLicence>
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        SupervisorViewOfWorkerImageHeader(worker)
        DriversLicenceRecord(worker, getWorkerDriverLicenceviewModel)
    }
}

@Composable
fun SupervisorViewOfWorkerImageHeader(
    worker: WorkerProfile
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .shadow(1.dp)
    ) {
        Surface(
            shadowElevation = 5.dp,
            shape = CircleShape,
            modifier = Modifier
                .size(150.dp)
                .offset(
                    x = (screenWidth - 150.dp) / 2,
                    y = 40.dp
                )

        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = worker.personalPhoto
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.four),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversLicenceRecord(
    worker: WorkerProfile,
    getWorkerDriverLicenceviewModel: KSuspendFunction1<String, DriversLicence>

){
    
val Licence = produceState(
    initialValue = DriversLicence(
        typeOfLicence = TypeOfLicence.Empty,
        licenceMap = emptyMap<String, Boolean>(),
        highestClass = HighestClass.Class1,
    ) ,
    producer = {
        value = getWorkerDriverLicenceviewModel(worker.email)
    }
        ).value

    val licenceMap = Licence.licenceMap

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["forks"]?.let {
                    Checkbox(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {}
                        )
                }
                Text(
                    text = "Forks",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["wheels"]?.let {
                    Checkbox(
                        checked = it,
                        enabled = false,
                        onCheckedChange = {})
                }
                Text(
                    text = "Wheels",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["rollers"]?.let {
                    Checkbox(
                        checked = it,
                        enabled = false,
                        onCheckedChange = {}
                    )
                }
                Text(
                    text = "Rollers",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["dangerousGoods"]?.let {
                    Checkbox(
                        checked = it,
                        enabled = false,
                        onCheckedChange = {}
                    )
                }
                Text(
                    text = "Dangerous Goods",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["tracks"]?.let {
                    Checkbox(
                        checked = it,
                        enabled = false,
                        onCheckedChange = {}
                    )
                }
                Text(
                    text = "Tracks",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

