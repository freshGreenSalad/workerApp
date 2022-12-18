package com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses

sealed class SuccessStatus() {
    class Success : SuccessStatus()
    class Failure : SuccessStatus()
}