package com.tamaki.workerapp.data.utility

object Routes {
    //server
    //private const val baseUrl = "https://tlc-nz.com/"
    //home
    //private const val baseUrl = "http://192.168.1.151:8080/"
    //nat and stus
    private const val baseUrl = "http://192.168.68.106:8080/"

    const val presignPutRequest = baseUrl + "s3PresignPut"

    const val WorkerSignupInfo = baseUrl + "WorkerSignupInfo"

    const val WorkerPersonalData = baseUrl + "WorkerPersonalData"

    const val WorkerDriversLicence = baseUrl + "WorkerDriversLicence"

    const val sendEmailPasswordGetJWT = baseUrl + "login"

    const val deleteWorkerAccount = baseUrl + "deleteAccount"

    const val SupervisorSiteInfo = baseUrl + "SupervisorSiteInfo"

    const val SupervisorPersonalData = baseUrl + "SupervisorPersonalData"

    const val getListOfWorkerAccounts = baseUrl + "getListOfWorkerAccounts"

}