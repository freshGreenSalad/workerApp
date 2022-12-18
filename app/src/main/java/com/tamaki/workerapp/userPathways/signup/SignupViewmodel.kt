package com.tamaki.workerapp.data.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.EmailPasswordIsSupervisorPushId
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
import com.onesignal.OneSignal
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.dataClasses.general.Location
import com.tamaki.workerapp.data.utility.Combine
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.destinations.SupervisorHomeScaffoldDestination
import com.tamaki.workerapp.userPathways.signup.Experience
import com.tamaki.workerapp.userPathways.signup.SignupRepository

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: YourRepository,
    private val dataStore: DataStore<Preferences>,
    private val signupRepository: SignupRepository
) : ViewModel() {

    suspend fun postEmailPasswordIsSupervisor() {
        DataStorePreferances(dataStore).edit("email",email.value)
        val authResult = repository.postAuthProfile(InitialiseEmailPasswordIsSupervisorFromState())
        resultChannelSignup.send(authResult)
    }

    suspend fun postPersonalWorkerLicenceExperience() {
        DataStorePreferances(dataStore).edit("email",email.value)
        val personalPhotoLink = repository.presigns3(photoUri.value)
        postWorkerLicence()
        postWorkerExperience()
        val workerProfile = InisiliseWorkerProfileFromState(personalPhotoLink)
        val postWorkerProfileResult = signupRepository.postWorkerProfile(workerProfile)
        resultChannelProfileBuild.send(postWorkerProfileResult)
    }

    suspend fun postSupervisorPersonalAndSite() {
        DataStorePreferances(dataStore).edit("email",email.value)
        val LinkToProfilePicture = repository.presigns3(photoUri.value)
        val site = InitialistSupervisorSiteFromState()
        val supervisorProfile = InitialiseSupervisorProfileFromState(LinkToProfilePicture)
        repository.postSupervisorSite(site)
        val postSupervisorProfileResult = repository.postSupervisorProfile(supervisorProfile)
        resultChannelProfileBuild.send(postSupervisorProfileResult)
    }

    private suspend fun postWorkerLicence() {
        val licence = InisialiseDriversLicenceFromState()
        signupRepository.postWorkerDriversLicence(licence = licence)
    }
    private suspend fun postWorkerExperience(){
        val experienceToSend = Experience(
            email = email.value,
            experience = experience.value,
            years = yearsExperience.value.toInt()
        )
        signupRepository.postWorkerExperience(experienceToSend)
    }

    fun handleSupervisorSignupRequest(
        result: authResult<Unit>,
        navigator: DestinationsNavigator,
        context: Context
    ) {
        when (result) {
            is authResult.authorised<Unit> -> navigator.navigate(SupervisorHomeScaffoldDestination)
            is authResult.unauthorised<Unit> -> {
                Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
            }
            is authResult.unknownError<Unit> -> {
                Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    private val resultChannelSignup = Channel<authResult<Unit>>()
    val authResults = resultChannelSignup.receiveAsFlow()
    private val resultChannelProfileBuild = Channel<authResult<Unit>>()
    val profileBuild = resultChannelProfileBuild.receiveAsFlow()

    private val _stateSignUp = MutableStateFlow(SignupState())

    val stateLogin: StateFlow<SignupState>
        get() = _stateSignUp

    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val isSupervisor = MutableStateFlow(true)
    private val firstname = MutableStateFlow("")
    private val lastname = MutableStateFlow("")
    private val workerCurrentEnum = MutableStateFlow(WorkerSignUpPoint.BasicInformation)
    private var supervisorCurrentEnum = MutableStateFlow(SupervisorSignupPoint.BasicInformation)
    private val currentWorkerStep = MutableStateFlow(0)
    private val currentSupervisorStep = MutableStateFlow(0)
    private var licenceType = MutableStateFlow(TypeOfLicence.Empty)
    private var forks = MutableStateFlow(false)
    private var wheels = MutableStateFlow(false)
    private var rollers = MutableStateFlow(false)
    private var dangerousGoods = MutableStateFlow(false)
    private var tracks = MutableStateFlow(false)
    private var highestClassState = MutableStateFlow(HighestClass.Class1)
    private var map = MutableStateFlow(MapDataClass())
    private var siteAddress = MutableStateFlow("")
    private var latLngAddress = MutableStateFlow(MarkerState(LatLng(1.35, 103.87)))
    private val shouldShowCamera = MutableStateFlow(true)
    private val shouldShowPhoto = MutableStateFlow(false)
    private val photoUri = MutableStateFlow(Uri.EMPTY)
    private val yearsExperience = MutableStateFlow(0f)
    private val experience = MutableStateFlow("")

    init {
        viewModelScope.launch {
            Combine().twentyFour(
                email,password,isSupervisor,firstname,lastname, workerCurrentEnum, supervisorCurrentEnum, currentWorkerStep,
                currentSupervisorStep,licenceType, forks,wheels,rollers,dangerousGoods,tracks, highestClassState, map, siteAddress,
                latLngAddress, shouldShowCamera, shouldShowPhoto, photoUri,yearsExperience,experience,
            ) { email,
                password,
                isSupervisor,
                firstname,
                lastname,
                workerSignUpPoint,
                supervisorSignUpScreenState,
                currentWorkerStep,
                currentSupervisorStep,
                licenceType,
                forks,
                wheels,
                rollers,
                dangerousGoods,
                tracks,
                highestClassState,
                map,
                siteAddress,
                latLngAddress,
                shouldShowCamera,
                shouldShowPhoto,
                photoUri,
                range,
                experience,->
                SignupState(
                    email,
                    password,
                    isSupervisor,
                    firstname,
                    lastname,
                    workerSignUpPoint,
                    supervisorSignUpScreenState,
                    currentWorkerStep,
                    currentSupervisorStep,
                    licenceType,
                    forks,
                    wheels,
                    rollers,
                    dangerousGoods,
                    tracks,
                    highestClassState,
                    map,
                    siteAddress,
                    latLngAddress,
                    shouldShowCamera,
                    shouldShowPhoto,
                    photoUri,
                    range,
                    experience,
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _stateSignUp.value = it
            }
        }
    }

    fun updateSiteAddress(address: String) { siteAddress.value = address }
    fun updateSitelatlng(latLng: LatLng) { latLngAddress.value.position = latLng }
    fun changeHighestClass(highestClass: HighestClass) { highestClassState.value = highestClass }
    fun updateFirstname(name: String) { firstname.value = name}
    fun updateLastname(name: String) { lastname.value = name }
    fun changeLicenceType(chosenLicenceType: TypeOfLicence) {licenceType.value = chosenLicenceType}
    fun UpdateForks(){forks.value != forks.value}
    fun UpdateWheels(){wheels.value != wheels.value}
    fun UpdateRollers(){rollers.value != rollers.value}
    fun UpdateDangerousGoods(){dangerousGoods.value != dangerousGoods.value}
    fun UpdateTracks(){tracks.value != tracks.value}
    fun shouldshowcam(bool: Boolean) { shouldShowCamera.value = bool }
    fun shouldshowPho(bool: Boolean) { shouldShowPhoto.value = bool }
    fun updatePhotoURI(uri: Uri) { photoUri.value = uri }
    fun incrementWorkerEnumPage(add: Int) {
        val enumList = WorkerSignUpPoint.values()
        currentWorkerStep.value = currentWorkerStep.value+add
        workerCurrentEnum.value = enumList[currentWorkerStep.value]
    }
    fun incrementSupervisorEnumPage(add: Int) {
        val enumList = SupervisorSignupPoint.values()
        currentSupervisorStep.value = currentSupervisorStep.value + add
        supervisorCurrentEnum.value = enumList[currentSupervisorStep.value]
    }
    fun updatePassword(newPassword: String) { password.value = newPassword }
    fun updateEmail(newEmail: String) { email.value = newEmail }
    fun updateIsSupervisor(newIsSupervisor: Boolean) { isSupervisor.value = newIsSupervisor }
    fun updateRange(newRange:Float){
        yearsExperience.value = newRange
    }
    fun updateExperience(newExperience:String){experience.value = newExperience}

    private fun InitialiseSupervisorProfileFromState(personalPhotoLink: String) =
        SupervisorProfile(
            email = email.value,
            firstName = firstname.value,
            lastName = lastname.value,
            personalPhoto = personalPhotoLink,
        )

    private fun InitialistSupervisorSiteFromState() = SupervisorSite(
        email = email.value,
        address = siteAddress.value,
        location = Location(
            Lat = latLngAddress.value.position.latitude,
            Lng = latLngAddress.value.position.longitude
        )
    )

    private fun InisiliseWorkerProfileFromState(personalPhotoLink: String) = WorkerProfile(
        email = email.value,
        firstName = firstname.value,
        lastName = lastname.value,
        personalPhoto = personalPhotoLink,
        rate = 35
    )
    private fun InisialiseDriversLicenceFromState():DriversLicence {
        val licenceMap = mapOf (
            "forks" to forks.value,
            "wheels" to wheels.value,
            "rollers" to rollers.value,
            "dangerousGoods" to dangerousGoods.value,
            "tracks" to tracks.value,
            )
        return DriversLicence(
        typeOfLicence = licenceType.value,
        licenceMap = licenceMap,
        highestClass = highestClassState.value
        )
    }

    private fun InitialiseEmailPasswordIsSupervisorFromState():EmailPasswordIsSupervisorPushId {
        val pushId = OneSignal.getDeviceState()?.userId!!
        return EmailPasswordIsSupervisorPushId(
            email.value,
            password.value,
            isSupervisor.value,
            pushId
        )
    }
}
data class SignupState(
    val email:String = "",
    val password:String = "",
    val isSupervisor:Boolean = true,
    val firstname:String = "",
    val lastname:String = "",
    val workerSignUpPoint: WorkerSignUpPoint = WorkerSignUpPoint.BasicInformation,
    val supervisorSignupPoint: SupervisorSignupPoint = SupervisorSignupPoint.BasicInformation,
    val currentWorkerStep:Int = 0,
    val currentSupervisorStep:Int = 0,
    val licenceType: TypeOfLicence = TypeOfLicence.Empty,
    val forks: Boolean = false,
    val wheels: Boolean = false,
    val rollers: Boolean = false,
    val dangerousGoods: Boolean = false,
    val tracks: Boolean = false,
    val highestClass: HighestClass = HighestClass.Class1,
    val googleMap: MapDataClass = MapDataClass(),
    val siteAddress: String = "",
    val latLngAddress: MarkerState = MarkerState(LatLng(1.35, 103.87)),
    val shouldShowCamera: Boolean = true,
    val shouldShowPhoto: Boolean = false,
    val photoUri: Uri = Uri.EMPTY,
    val yearsExperience:Float = 0f,
    val experience:String = "",
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

enum class SupervisorSignupPoint {
    BasicInformation, Map
}

enum class WorkerSignUpPoint {
    BasicInformation,  Tickets, Experience,
}

enum class TypeOfLicence {
    Learners, Restricted, Full, Empty
}

enum class HighestClass {
    Class1, Class2, Class3, Class4, Class5
}