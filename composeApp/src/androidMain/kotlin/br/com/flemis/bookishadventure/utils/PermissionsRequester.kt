/*

package br.com.flemis.bookishadventure.utils

import android.Manifest
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import br.com.flemis.bookishadventure.MainActivity
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionsRequester(
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>>
) {
    // private var onPermissionResult: ((PermissionStatus) -> Unit)? = null
    private var continuation: CancellableContinuation<PermissionStatus>? = null


*/
/* fun onResult(perms: Map<String, Boolean>, permissions: List<String>) {


/*val denied = perms.filterValues { !it }.keys
        val granted = perms.filterValues { it }.keys
        Log.d("perms", "onResult: ${perms}")
        val status = when {
            denied.isEmpty() -> PermissionStatus.Granted
            granted.isNotEmpty() && denied.isNotEmpty() -> PermissionStatus.PartiallyGranted
            denied.any() -> PermissionStatus.Denied
            else -> PermissionStatus.PermanentlyDenied
        }
        continuation?.resume(status)
        continuation = null*//*



        permissions.forEach { permission ->
            when (perms[permission]) {
                true -> {
                    Log.d("PermissionsRequester", "Permission granted: $permission")
                    continuation?.resume(PermissionStatus.Granted)
                    continuation = null
                }
                false -> {
                    Log.d("PermissionsRequester", "Permission denied: $permission")
                    continuation?.resume(PermissionStatus.Denied)
                    continuation = null
                }
                null -> {
                    Log.d("PermissionsRequester", "Permission not determined: $permission")
                    continuation?.resume(PermissionStatus.NotDetermined)
                    continuation = null
                }
            }
        }
    }*//*


 */
/*   fun onResult(perms:Map<String,Boolean>, permissions:List<String>){
        perms.keys.forEach {
            when (perms[it]) {
                true -> {
                    Log.d("PermissionsRequester", "Permission granted: $it")
                    continuation?.resume(PermissionStatus.Granted)
                }
                false -> {
                    Log.d("PermissionsRequester", "Permission denied: $it")
                    continuation?.resume(PermissionStatus.Denied)
                }
                null -> {
                    Log.d("PermissionsRequester", "Permission not determined: $it")
                    continuation?.resume(PermissionStatus.NotDetermined)
                }
            }
        }
    }*//*


    fun onResult(perms: Map<String, Boolean>) {
        Log.d("PermissionsRequester", "onResult: $perms")
       perms.forEach { (permission,isGranted)->
           when (permission){
                Manifest.permission.CAMERA -> {
                     if (isGranted) {
                          Log.d("PermissionsRequester", "Camera permission granted")
                          continuation?.resume(PermissionStatus.Granted)
                     } else {
                          Log.d("PermissionsRequester", "Camera permission denied")
                          continuation?.resume(PermissionStatus.Denied)
                     }
                }
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                     if (isGranted) {
                          Log.d("PermissionsRequester", "Read External Storage permission granted")
                          continuation?.resume(PermissionStatus.Granted)
                     } else {
                          Log.d("PermissionsRequester", "Read External Storage permission denied")
                          continuation?.resume(PermissionStatus.Denied)
                     }
                }
                else -> {
                     Log.d("PermissionsRequester", "Unknown permission: $permission")
                     continuation?.resume(PermissionStatus.NotDetermined)
                }
           }
       }
    }


    fun requestPermissions(permissions: List<String>, onResult: (Map<String, Boolean>) -> Unit): Unit =
        when (permissions) {
            is List<String> -> {
                Log.d("PermissionsRequester", "Requesting permissions: $permissions")
                requestPermissionLauncher.launch(permissions.toTypedArray())
                // onResult(permissions.toMap())
                // onPermissionResult = onResult
            }

            else -> throw IllegalArgumentException("Unsupported permissions type: ${permissions::class.java}")
        }


}
*/