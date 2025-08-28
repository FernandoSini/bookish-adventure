package br.com.flemis.bookishadventure.utils

import br.com.flemis.bookishadventure.utils.exceptions.PermissionDeniedException
import kotlinx.coroutines.suspendCancellableCoroutine

import platform.AVFoundation.*
import platform.CoreLocation.*
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class PermissionHandler {
    actual suspend fun requestPermission(permission: String): PermissionStatus {
        //var status: PermissionStatus = PermissionStatus.NotDetermined;
        val iosHandler = IOSPermissionHandler()
        val status = suspendCancellableCoroutine { continuation ->
            iosHandler.requestPermission(permission) { result ->
                val mappedStatus = when (result) {
                    IOSPermissionStatus.Granted, IOSPermissionStatus.Restricted -> PermissionStatus.Granted
                    IOSPermissionStatus.Denied -> PermissionStatus.Denied
                    IOSPermissionStatus.NotDetermined -> PermissionStatus.NotDetermined
                  //  else -> PermissionStatus.NotDetermined
                }
                continuation.resume(mappedStatus)
            }

        }
        return status
    }

        actual suspend fun openAppSettings() {
            val url = NSURL(string = UIApplicationOpenSettingsURLString)
            if (UIApplication.sharedApplication.canOpenURL(url)) {
                UIApplication.sharedApplication.openURL(url, options = emptyMap<Any?, Any>(), completionHandler = null)
            } else {
                throw IllegalStateException("Cannot open app settings")
            }
        }

        actual suspend fun verifyPermission(permission: String, callback: (PermissionStatus) -> Unit): Boolean {
            val iosHandler = IOSPermissionHandler()
            return when (iosHandler.getPermissionStatus(permission)) {
                IOSPermissionStatus.Granted -> {
                    callback(PermissionStatus.Granted)
                    true
                }

                IOSPermissionStatus.Denied -> {
                    callback(PermissionStatus.Denied)
                    false
                    //  throw PermissionDeniedException("$permission is denied", Throwable())
                }

                IOSPermissionStatus.NotDetermined -> {
                    callback(PermissionStatus.NotDetermined)
                    false
                }

                IOSPermissionStatus.Restricted -> {
                    callback(PermissionStatus.PartiallyGranted)
                    true
                }

               /* else -> {
                    callback(PermissionStatus.NotDetermined)
                    false
                }*/
            }

        }


    }

/*actual class PermissionHandler {
    actual suspend fun requestPermissions(permissions: List<String>): Map<String, PermissionStatus> {
        val handler = IOSPermissionHandler()

        val results = mutableMapOf<String, PermissionStatus>()
        for (permission in permissions) {
            if (permission == null) {
                throw IllegalArgumentException("Permission $permission is not recognized")
            }
            val status = suspendCancellableCoroutine { continuation ->
                handler.requestPermission(permission) { result ->
                    val mappedStatus = when (result) {
                        IOSPermissionStatus.Granted -> PermissionStatus.Granted
                        IOSPermissionStatus.Denied -> PermissionStatus.Denied
                        IOSPermissionStatus.Denied -> PermissionStatus.PermanentlyDenied
                        IOSPermissionStatus.NotDetermined -> PermissionStatus.NotDetermined
                        else -> PermissionStatus.Denied
                    }
                    continuation.resumeWith(Result.success(mappedStatus))
                }
            }
            results[permission] = status
            if (results[permission] == PermissionStatus.NotDetermined) {
                openAppSettings()
            }
        }
        return results
    }

    actual suspend fun openAppSettings() {
        val url = NSURL(string = UIApplicationOpenSettingsURLString)
        if (url != null && UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        } else {
            throw IllegalStateException("Cannot open app settings")
        }
    }

    actual suspend fun verifyPermissions(permissions: List<String>): Map<String, PermissionStatus> {
        val status = requestPermissions(permissions)
        return status;
    }


}*/

/*actual fun getInstancePermissionHandler(): PermissionHandler {
    return PermissionHandler()
}*/







