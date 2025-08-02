package br.com.flemis.bookishadventure.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import br.com.flemis.bookishadventure.MainActivity
import br.com.flemis.bookishadventure.MyApplication
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.definition.Callbacks
import kotlin.coroutines.resume

actual class PermissionHandler(private val context: Context) {
    actual constructor(): this(MyApplication.applicationContext())
    private var permissionListener: PermissionListener? = null

    internal fun initializeInterface(permissionListenerInitializer: PermissionListener) {
        permissionListener = permissionListenerInitializer;
    }

    actual suspend fun requestPermission(permission: String) {
        permissionListener?.requestPermission(permission)
    }

    actual suspend fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    actual suspend fun verifyPermission(permission: String): PermissionStatus {
        val result = permissionListener?.getPermissionStatus(permission)
        return when (result) {
            PermissionStatus.Granted -> PermissionStatus.Granted
            PermissionStatus.Denied -> PermissionStatus.Denied
            PermissionStatus.PermanentlyDenied -> PermissionStatus.PermanentlyDenied
            PermissionStatus.PartiallyGranted -> PermissionStatus.PartiallyGranted
            PermissionStatus.NotDetermined -> PermissionStatus.NotDetermined
            else -> PermissionStatus.NotDetermined
        }
    }


}

/*actual class PermissionHandler(private val context: Context, private val permissionsRequester: PermissionsRequester) {

    private lateinit var pendingPermissions: List<String>


    /* actual suspend fun requestPermissions(permissions: List<String>): Map<String, PermissionStatus> {
         val results = mutableMapOf<String, PermissionStatus>()
         val status = permissionsRequester.requestPermissions(permissions)
         Log.d("PermissionHandler", "requestPermissions: ${status}")
         when (status) {
             PermissionStatus.Granted -> {
                 permissions.forEach { permission ->
                     results[permission] = PermissionStatus.Granted
                 }
             }

             PermissionStatus.Denied -> {
                 permissions.forEach { permission ->
                     results[permission] = PermissionStatus.Denied
                 }
             }

             PermissionStatus.PermanentlyDenied -> {
                 permissions.forEach { permission ->
                     results[permission] = PermissionStatus.PermanentlyDenied
                 }
             }

             PermissionStatus.PartiallyGranted -> {
                 permissions.forEach { permission ->
                     results[permission] = PermissionStatus.PartiallyGranted
                 }
             }

             PermissionStatus.NotDetermined -> openAppSettings()
         }

         return results
     }*/

    actual suspend fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)

    }

    actual suspend fun verifyPermissions(permissions: List<String>): Map<String, PermissionStatus> {
        return requestPermissions(permissions)
    }

    actual suspend fun requestPermissions(permissions: List<String>): Map<String, PermissionStatus> {
        val results = mutableMapOf<String, PermissionStatus>()
        val status = suspendCancellableCoroutine { continuation ->
            permissionsRequester.requestPermissions(permissions) { requestedPermission ->
                Log.d("PermissionHandler", "permissions: ${requestedPermission}")
                val mappedStatus = when (requestedPermission) {
                    PermissionStatus.Granted -> PermissionStatus.Granted
                    PermissionStatus.Denied -> PermissionStatus.Denied
                    PermissionStatus.PermanentlyDenied -> PermissionStatus.PermanentlyDenied
                    PermissionStatus.PartiallyGranted -> PermissionStatus.PartiallyGranted
                    PermissionStatus.NotDetermined -> PermissionStatus.NotDetermined
                    else -> PermissionStatus.Denied
                }
                continuation.resume(mappedStatus)

            }
        }
        permissions.forEach { permission ->
            results[permission] = status
            if (results[permission] == PermissionStatus.NotDetermined) {
                openAppSettings()
            }

        }
        return results
    }

    /*
    *
    *  actual suspend fun requestPermissions(permissions: List<String>): Map<String, PermissionStatus> {
        val results = mutableMapOf<String, PermissionStatus>()
        val statusMap = suspendCancellableCoroutine<Map<String, PermissionStatus>> { continuation ->
            permissionsRequester.requestPermissions(permissions) { permissionStatusMap ->
                continuation.resume(permissionStatusMap)
            }
        }
        for (permission in permissions) {
            val status = statusMap[permission] ?: PermissionStatus.Denied
            results[permission] = status
            if (status == PermissionStatus.NotDetermined) {
                openAppSettings()
            }
        }
        return results
    }
  }
 */
    *
    * */











