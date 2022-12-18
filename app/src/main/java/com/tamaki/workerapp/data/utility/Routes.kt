package com.tamaki.workerapp.data.utility

object Routes {
    //server
    //private const val baseUrl = "https://tlc-nz.com/"
    //home
    //private const val baseUrl = "http://192.168.1.151:8080/"
    //nat and stus
    private const val baseUrl = "http://192.168.1.151:8080/"

    const val presignPutRequest = baseUrl + "s3PresignPut"

    const val WorkerSignupInfo = baseUrl + "WorkerSignupInfo"

    const val WorkerPersonalData = baseUrl + "WorkerPersonalData"

    const val WorkerDriversLicence = baseUrl + "WorkerDriversLicence"

    const val sendEmailPasswordGetJWT = baseUrl + "SignupInfo"

    const val deleteWorkerAccount = baseUrl + "deleteAccount"

    const val SupervisorSiteInfo = baseUrl + "SupervisorSiteInfo"

    const val SupervisorPersonalData = baseUrl + "SupervisorPersonalData"

    const val getListOfWorkerAccounts = baseUrl + "getListOfWorkerAccounts"

    const val hireWorker = baseUrl + "hireWorker"

    const val SearchForWorkers = baseUrl + "SearchForWorkers"

    const val WorkerExperience = baseUrl + "WorkerExperience"

    const val sendnotification = baseUrl + ""
}