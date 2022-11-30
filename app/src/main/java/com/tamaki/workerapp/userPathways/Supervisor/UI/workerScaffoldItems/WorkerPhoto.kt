package com.tamaki.workerapp.ui.workerInfoPage.workerUITabs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tamaki.workerapp.R
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.TypeOfLicence
import kotlin.reflect.KSuspendFunction1

@Composable
fun WorkerPhotoTab(
    paddingValues: PaddingValues,
    worker: WorkerProfile,
    getWorkerDriverLicenceviewModel: KSuspendFunction1<String, DriversLicence>
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        item {
            SupervisorViewOfWorkerImageHeader(worker)
        }
        item {
            DriversLicenceRecord(worker, getWorkerDriverLicenceviewModel)
        }
        item{
            WorkersExperience()
        }
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

) {

    val Licence = produceState(
        initialValue = DriversLicence(
            typeOfLicence = TypeOfLicence.Empty,
            licenceMap = emptyMap<String, Boolean>(),
            highestClass = HighestClass.Class1,
        ),
        producer = {
            value = getWorkerDriverLicenceviewModel(worker.email)
        }
    ).value

    val licenceMap = Licence.licenceMap

    if (Licence.typeOfLicence == TypeOfLicence.Empty) {

        Text(
            modifier = Modifier
                .padding(5.dp)
                .alpha(.7f), style = MaterialTheme.typography.headlineSmall,
            text = worker.firstName + " Does Not Have A Licence"
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(.7f), style = MaterialTheme.typography.headlineSmall,
                text = worker.firstName + "'s Licence"
            )
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Text(
                text = "Type Of Licence:" + Licence.typeOfLicence.toString(),
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(.7f), style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Class:" + Licence.highestClass.toString(),
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(.7f), style = MaterialTheme.typography.headlineSmall
            )
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
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["wheels"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = {}
                    )
                }
                Text(
                    text = "Wheels",
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
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
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
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
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
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
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
fun WorkersExperience(

){
    Text(
        text = "Tracks",
        modifier = Modifier
            .padding(5.dp)
            .alpha(.7f), style = MaterialTheme.typography.headlineSmall
    )
}
