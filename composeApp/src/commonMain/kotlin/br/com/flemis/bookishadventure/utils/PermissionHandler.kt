package br.com.flemis.bookishadventure.utils

import br.com.flemis.bookishadventure.utils.exceptions.PermissionDeniedException


sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
    object PartiallyGranted : PermissionStatus()
    object NotDetermined : PermissionStatus()
    // object GrantedOnlyRunningApp : PermissionStatus()
}

expect class PermissionHandler {
    constructor()
    suspend fun requestPermission(permission: String): PermissionStatus
    suspend fun openAppSettings()

    suspend fun verifyPermission(permission: String, callback:(PermissionStatus)->Unit): Boolean
}



