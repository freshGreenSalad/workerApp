package com.tamaki.workerapp.data.utility

object Routes {
    //server
    //private const val baseUrl = "https://tlc-nz.com/"
    //home
    //private const val baseUrl = "http://192.168.1.151:8080/"
    //nat and stus
    private const val baseUrl = "http://192.168.68.106:8080/"

    const val presignPutRequest = baseUrl + "s3PresignPut"

    const val putWorkerSignupInfo = baseUrl + "putWorkerSignupInfo"

    const val putWorkerPersonalData = baseUrl + "putWorkerPersonalData"

    const val putWorkerDriversLicence = baseUrl + "putWorkerDriversLicence"

    const val getWorkerSignupInfo = baseUrl + "getWorkerSignupAuth"

    const val getWorkerPersonalData = baseUrl + "getWorkerPersonalData"

    const val getWorkerDriversLicence = baseUrl + "getWorkerDriversLicence"

    const val deleteWorkerAccount = baseUrl + "deleteAccount"

    const val putSupervisorSiteInfo = baseUrl + "putSupervisorSiteInfo"

    const val putSupervisorPersonalData = baseUrl + "putSupervisorPersonalData"

    const val getSupervisorPersonalData = baseUrl + "getSupervisorPersonalData"

    const val getListOfWorkerAccounts = baseUrl + "getListOfWorkerAccounts"

}