package com.tamaki.workerapp.data.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.room.YourRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.dataClasses.general.Location
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.destinations.MainHolderComposableDestination

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: YourRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val resultChannelSignup = Channel<authResult<Unit>>()

    val authResults = resultChannelSignup.receiveAsFlow()

    suspend fun postAuthProfile() {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(name = "email")] = email.value
        }

        val authResult = repository.postAuthProfile(
            ProfileLoginAuthRequestWithIsSupervisor(
                email.value,
                password.value,
                isSupervisor.value
            )
        )
        resultChannelSignup.send(authResult)
    }

    private val resultChannelProfileBuild = Channel<authResult<Unit>>()

    val profileBuild = resultChannelProfileBuild.receiveAsFlow()

    suspend fun postPersonalWorker() {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(name = "email")] = email.value
        }

        val personalPhotoLink = repository.presigns3(photoUri.value)

        val licence = DriversLicence(
            typeOfLicence = licenceType.value,
            licenceMap = licenceMap.value,
            highestClass = highestClassState.value
        )

        val workerProfile = WorkerProfile(
            email = email.value,
            firstName = WorkerFirstname.value,
            lastName = WorkerLastname.value,
            personalPhoto = personalPhotoLink,
            rate = 35
        )

        repository.postWorkerDriversLicence(licence = licence)

        val postWorkerProfileResult = repository.postWorkerProfile(workerProfile)


        resultChannelProfileBuild.send(postWorkerProfileResult)
    }

    suspend fun postSupervisorPersonalAndSite() {

        dataStore.edit { settings ->
            settings[stringPreferencesKey(name = "email")] = email.value
        }

        val personalPhotoLink = repository.presigns3(photoUriSupervisor.value)

        val site = SupervisorSite(
            email = email.value,
            address = siteAddress.value,
            location = Location(
                Lat = latLngAddress.value.position.latitude,
                Lng = latLngAddress.value.position.longitude
            )
        )

        val supervisorProfile = SupervisorProfile(
            email = email.value,
            firstName = supervisorFirstName.value,
            lastName = supervisorLastName.value,
            personalPhoto = personalPhotoLink,
        )

        repository.postSupervisorSite(site)

        val postSupervisorProfileResult = repository.postSupervisorProfile(supervisorProfile)

        resultChannelProfileBuild.send(postSupervisorProfileResult)
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateMap = MutableStateFlow(MapState())

    val stateMap: StateFlow<MapState>
        get() = _stateMap

    private var map = MutableStateFlow(MapDataClass())

    private var siteAddress = MutableStateFlow("")

    private var latLngAddress = MutableStateFlow(MarkerState(LatLng(1.35, 103.87)))

    private var mapScreenShoturi = MutableStateFlow(Uri.EMPTY)

    init {
        viewModelScope.launch {
            combine(
                map,
                siteAddress,
                latLngAddress,
                mapScreenShoturi
            ) { map, siteAddress, latLngAddress, mapScreenShoturi ->
                MapState(
                    map,
                    siteAddress,
                    latLngAddress,
                    mapScreenShoturi
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateMap.value = it
            }
        }
    }

    fun returnUri(uri: Uri) {
        mapScreenShoturi.value = uri
    }

    fun updateSiteAddress(address: String) {
        siteAddress.value = address
    }

    fun updateSitelatlng(latLng: LatLng) {
        latLngAddress.value.position = latLng
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateExperience = MutableStateFlow(Experience())

    val stateExperience: StateFlow<Experience>
        get() = _stateExperience

    private var experienceTypeState = MutableStateFlow(ExperienceType.Formwork)

    private var formworkMap = MutableStateFlow(
        mutableStateMapOf(
            Pair("Docka", false),
        )
    )

    init {
        viewModelScope.launch {
            combine(
                experienceTypeState,
                formworkMap,
            ) { experienceType, formworkMap ->
                Experience(
                    experienceType,
                    formworkMap,
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateExperience.value = it
            }
        }
    }

    fun updateExperienceType(experienceType: ExperienceType) {
        experienceTypeState.value = experienceType
    }

    fun updateFormworkMap(FormworkVar: String) {
        formworkMap.value[FormworkVar] = !formworkMap.value[FormworkVar]!!
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateSupervisorScaffold = MutableStateFlow(SupervisorState())

    val stateSupervisorScaffold: StateFlow<SupervisorState>
        get() = _stateSupervisorScaffold

    private var supervisorSignUpScreenState =
        MutableStateFlow(SupervisorSignupPoint.BasicInformation)

    private val currentSupervisorStep = MutableStateFlow(0)

    private val supervisorFirstName = MutableStateFlow("")

    private val supervisorLastName = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                supervisorFirstName,
                supervisorLastName,
                supervisorSignUpScreenState,
                currentSupervisorStep,
            ) { supervisorFirstName, supervisorLastName, supervisorSignUpScreenState, currentSupervisorStep ->
                SupervisorState(
                    supervisorFirstName,
                    supervisorLastName,
                    supervisorSignUpScreenState,
                    currentSupervisorStep,
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateSupervisorScaffold.value = it
            }
        }
    }

    private val supervisorSignUpPoint =
        listOf(SupervisorSignupPoint.BasicInformation, SupervisorSignupPoint.Map)

    fun nextSupervisorScreen(add: Int) {
        supervisorSignUpScreenState.value = supervisorSignUpPoint[currentSupervisorStep.value + add]
        currentSupervisorStep.value = currentSupervisorStep.value + add
    }

    fun updateSupervisorFirstName(name: String) {
        supervisorFirstName.value = name
    }

    fun updateSupervisorLastName(name: String) {
        supervisorLastName.value = name
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateName = MutableStateFlow(NameState())

    val stateName: StateFlow<NameState>
        get() = _stateName

    private var WorkerFirstname = MutableStateFlow("")

    private var WorkerLastname = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                WorkerFirstname,
                WorkerLastname,
            ) { firstname, lastname ->
                NameState(
                    firstname,
                    lastname,
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateName.value = it
            }
        }
    }

    fun updateWorkerFirstname(name: String) {
        WorkerFirstname.value = name
    }

    fun updateWorkerLastname(name: String) {
        WorkerLastname.value = name
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateTickets = MutableStateFlow(TicketState())

    val stateTickets: StateFlow<TicketState>
        get() = _stateTickets

    private var ticketTypeState = MutableStateFlow(TicketType.DriversLicence)

    private var licenceType = MutableStateFlow(TypeOfLicence.Empty)

    private var licenceMap = MutableStateFlow(
        mutableStateMapOf(
            Pair("forks", false),
            Pair("wheels", false),
            Pair("rollers", false),
            Pair("dangerousGoods", false),
            Pair("tracks", false)
        )
    )

    private var highestClassState = MutableStateFlow(HighestClass.Class1)

    init {
        viewModelScope.launch {
            combine(
                ticketTypeState,
                licenceType,
                licenceMap,
                highestClassState

            ) { ticketType, licenceType, licenceMap, highestClass ->
                TicketState(
                    ticketType = ticketType,
                    licenceType = licenceType,
                    licenceMap = licenceMap,
                    highestClass = highestClass
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateTickets.value = it
            }
        }
    }

    fun changeHighestClass(highestClass: HighestClass) {
        highestClassState.value = highestClass
    }

    fun changeTicketType(ticketType: TicketType) {
        ticketTypeState.value = ticketType
    }

    fun changeLicenceType(chosenLicenceType: TypeOfLicence) {
        licenceType.value = chosenLicenceType
    }

    fun updateLicenceMap(licenceVar: String) {
        licenceMap.value[licenceVar] = !licenceMap.value[licenceVar]!!
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateCamera = MutableStateFlow(CameraState())

    val stateCamera: StateFlow<CameraState>
        get() = _stateCamera

    private val shouldShowCamera = MutableStateFlow(true)

    private val shouldShowPhoto = MutableStateFlow(false)

    private val photoUri = MutableStateFlow(Uri.EMPTY)

    init {
        viewModelScope.launch {
            combine(
                shouldShowCamera,
                shouldShowPhoto,
                photoUri
            ) { shouldShowCamera, shouldShowPhoto, photoUri ->
                CameraState(
                    shouldShowCamera,
                    shouldShowPhoto,
                    photoUri
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateCamera.value = it
            }
        }
    }

    fun shouldshowcam(bool: Boolean) {
        shouldShowCamera.value = bool
    }

    fun shouldshowPho(bool: Boolean) {
        shouldShowPhoto.value = bool
    }

    fun updatePhotoURI(uri: Uri) {
        photoUri.value = uri
        println(photoUri.value.path)
    }

    //______________________________________________________________________________________________________________________________________________

    private val _stateSupervisorCamera = MutableStateFlow(CameraSupervisorState())

    val stateSupervisorCamera: StateFlow<CameraSupervisorState>
        get() = _stateSupervisorCamera

    private val shouldShowSupervisorCamera = MutableStateFlow(true)

    private val shouldShowSupervisorPhoto = MutableStateFlow(false)

    private val photoUriSupervisor = MutableStateFlow(Uri.EMPTY)

    init {
        viewModelScope.launch {
            combine(
                shouldShowSupervisorCamera,
                shouldShowSupervisorPhoto,
                photoUriSupervisor
            ) { shouldShowSupervisorCamera, shouldShowSupervisorPhoto, photoUriSupervisor ->
                CameraSupervisorState(
                    shouldShowSupervisorCamera,
                    shouldShowSupervisorPhoto,
                    photoUriSupervisor
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateSupervisorCamera.value = it
            }
        }
    }

    fun shouldshowSupervisorcam(bool: Boolean) {
        shouldShowSupervisorCamera.value = bool
    }

    fun shouldshowSupervisorPho(bool: Boolean) {
        shouldShowSupervisorPhoto.value = bool
    }

    fun updateSupervisorPhotoURI(uri: Uri) {
        photoUriSupervisor.value = uri
        println(photoUriSupervisor.value.path)
    }

    //______________________________________________________________________________________________________________________________________________
    private val _state = MutableStateFlow(ProfileCreationPageState())

    val state: StateFlow<ProfileCreationPageState>
        get() = _state

    private val workerSignUpPoint = MutableStateFlow(WorkerSignUpPoint.BasicInformation)

    private val currentStep = MutableStateFlow(0)
    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val isSupervisor = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            combine(
                email,
                password,
                isSupervisor,
                workerSignUpPoint,
                currentStep
            ) { email, password, isSupervisor, workerSignUpPoint, currentStep ->
                ProfileCreationPageState(
                    email = email,
                    password = password,
                    isSupervisor = isSupervisor,
                    workerSignUpPoint = workerSignUpPoint,
                    currentStep = currentStep
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }


    private val signUpPoint = listOf(
        WorkerSignUpPoint.BasicInformation,
        WorkerSignUpPoint.Tickets,
        WorkerSignUpPoint.Experience
    )

    fun nextScreen(add: Int) {
        workerSignUpPoint.value = signUpPoint[currentStep.value + add]
        currentStep.value = currentStep.value + add
    }

    fun handleSupervisorSignupRequest(
        result: authResult<Unit>,
        navigator: DestinationsNavigator,
        context: Context
    ) {
        when (result) {
            is authResult.authorised<Unit> -> navigator.navigate(MainHolderComposableDestination)
            is authResult.unauthorised<Unit> -> {
                Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
            }
            is authResult.unknownError<Unit> -> {
                Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updateIsSupervisor(newIsSupervisor: Boolean) {
        isSupervisor.value = newIsSupervisor
    }
    //______________________________________________________________________________________________________________________________________________

}

enum class WorkerSignUpPoint {
    BasicInformation, Experience, Tickets
}

enum class TypeOfLicence {
    Learners, Restricted, Full, Empty
}

enum class HighestClass {
    Class1, Class2, Class3, Class4, Class5
}

enum class TicketType {
    Crane, DangerousSpaces, DriversLicence, Lifts, Empty
}

data class CameraState constructor(
    val shouldShowCamera: Boolean = true,
    val shouldShowPhoto: Boolean = false,
    val photoUri: Uri = Uri.EMPTY
)

data class CameraSupervisorState constructor(
    val shouldShowCamera: Boolean = true,
    val shouldShowPhoto: Boolean = false,
    val photoUri: Uri = Uri.EMPTY
)

data class NameState constructor(
    val firstname: String = "",
    val lastname: String = "",
)

data class TicketState constructor(
    val ticketType: TicketType = TicketType.DriversLicence,
    val licenceType: TypeOfLicence = TypeOfLicence.Empty,
    val licenceMap: MutableMap<String, Boolean> = mutableMapOf(Pair("", true)),
    val highestClass: HighestClass = HighestClass.Class1
)

data class ProfileCreationPageState constructor(
    val workerSignUpPoint: WorkerSignUpPoint = WorkerSignUpPoint.BasicInformation,
    val email: String = "",
    val password: String = "",
    val isSupervisor: Boolean = true,
    val currentStep: Int = 0
)

data class MapDataClass(
    val properties: MapProperties = MapProperties(
        isBuildingEnabled = false,
        isIndoorEnabled = false,
        isMyLocationEnabled = false,
        isTrafficEnabled = false,
        latLngBoundsForCameraTarget = null,
        mapType = MapType.NORMAL,
        maxZoomPreference = 20f,
        minZoomPreference = 0f
    ),
)

data class MapState(
    val map: MapDataClass = MapDataClass(),
    val siteAddress: String = "",
    val latLngAddress: MarkerState = MarkerState(LatLng(1.35, 103.87)),
    val mapScreenShoturi: Uri = Uri.EMPTY
)

data class Experience(
    val experienceType: ExperienceType = ExperienceType.Formwork,
    val formworkMap: MutableMap<String, Boolean> = mutableMapOf(Pair("", true))
)

enum class ExperienceType {
    Formwork, Machinery, Reinforcing, Rigging
}

enum class SupervisorSignupPoint {
    BasicInformation, Map
}

data class SupervisorState(
    val supervisorFirstName: String = "",
    val supervisorLastName: String = "",
    val supervisorSignupPoint: SupervisorSignupPoint = SupervisorSignupPoint.BasicInformation,
    val currentSupervisorStep: Int = 0
)

