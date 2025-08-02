package br.com.flemis.bookishadventure

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import br.com.flemis.bookishadventure.features.app.presentation.pages.App
import br.com.flemis.bookishadventure.core.di.androidModule
import br.com.flemis.bookishadventure.utils.PermissionListener
import br.com.flemis.bookishadventure.utils.PermissionStatus
import br.com.flemis.bookishadventure.utils.Preferences
import br.com.flemis.bookishadventure.utils.SharedViewModel
import br.com.flemis.bookishadventure.utils.restoreSharedViewModel
import br.com.flemis.bookishadventure.utils.saveSharedViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import kotlin.Result.Companion.success


class MainActivity : ComponentActivity(), PermissionListener {


    /*private val permissions: Array<String> = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.READ_MEDIA_IMAGES,
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.WRITE_CALENDAR,
        android.Manifest.permission.READ_CALENDAR
    )*/
    internal var permissions = listOf<String>("camera", "location", "storage", "microphone", "gallery", "calendar")

    //  private lateinit var permissionRequester: PermissionsRequester;
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>;

    private val sharedViewModel: SharedViewModel by viewModels<SharedViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.forEach {
                when (permissions[it.key]) {
                    true -> {
                        Napier.i { "Permission granted: ${it.key}" }
                        success("Permission granted: ${it.key}".also { Napier.i(it) })
                    }

                    false -> {
                        Napier.i { "Permission denied: ${it.key}" }
                        error("Permission denied: ${it.key}".also { Napier.i(it) })


                    }

                    null -> launchPermission(it.key)
                }

            }



            loadKoinModules(androidModule(this))

            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                    detectDarkMode = { resources ->
                        Preferences().getBoolean("darkMode", false)
                    }),
                navigationBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                    detectDarkMode = { resources -> true })
            )
            // Firebase.initialize(this)
            LanguageManager(this)
            setContent {
                App()
            }
        }
    }


    private fun _inicializePermissionLauncher(onResult: (Map<String, Boolean>) -> Unit): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            callback = {
                onResult.invoke(it)

            }
        )
    }

    private fun launchPermission(permission: String) {
        when (permission) {
            "camera" -> {
                permissionLauncher.launch(listOf(Manifest.permission.CAMERA).toTypedArray())
            }

            "gallery", "storage" -> {
                if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(
                        listOf(
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_AUDIO
                        ).toTypedArray()
                    )

                } else {
                    permissionLauncher.launch(
                        listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).toTypedArray()
                    )

                }

            }

            "microphone" -> {
                permissionLauncher.launch(
                    listOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
                    ).toTypedArray()
                )

            }

            "location" -> {
                if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(
                        listOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.FOREGROUND_SERVICE_LOCATION,
                            Manifest.permission.ACCESS_MEDIA_LOCATION,
                            Manifest.permission.CONTROL_LOCATION_UPDATES,
                            Manifest.permission.NEARBY_WIFI_DEVICES
                        ).toTypedArray()
                    )

                } else {
                    permissionLauncher.launch(
                        listOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.FOREGROUND_SERVICE_LOCATION,
                            Manifest.permission.ACCESS_MEDIA_LOCATION
                        ).toTypedArray()
                    )
                }
            }

            "notifications" -> {
                if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(listOf(Manifest.permission.POST_NOTIFICATIONS).toTypedArray())
                } else {
                    success("Notifications permission not required for this SDK version".also { Napier.i(it) })
                }
            }

            "calendar" -> {
                permissionLauncher.launch(
                    listOf(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR
                    ).toTypedArray()
                )
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSharedViewModel(sharedViewModel, outState)

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("seu pai2 ${savedInstanceState}")
        restoreSharedViewModel(sharedViewModel, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(
            androidModule(this)
        )
    }

    override fun requestPermissions(permissions: List<String>) {
        TODO("Not yet implemented")
    }

    override fun requestPermission(permission: String) {

    }


    override fun getPermissionStatus(
        permission: String
    ): PermissionStatus {
        return when (this.checkSelfPermission(permission)) {
            PERMISSION_GRANTED -> {
                return PermissionStatus.Granted
            }

            PERMISSION_DENIED -> {
                if (this.shouldShowRequestPermissionRationale(permission)) {
                    return PermissionStatus.Denied
                } else {
                    return PermissionStatus.Denied
                }
            }

            else -> {

                return PermissionStatus.NotDetermined
            }
        }
    }

    override fun openAppSetings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        this.startActivity(intent)
    }


}

/*fun Activity.openAppSettings() {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    this.startActivity(intent)
}*/

/* private val requestPermissionLauncher = this.registerForActivityResult(
       ActivityResultContracts.RequestPermission()
   ) { isGranted: Boolean ->
       // Callback para o resultado da permissão
       permissionRequester.handlePermissionResult(isGranted)
   }*/
/*
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}*/

/*
*
*  when (permission) {
                    "camera" -> {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }

                    "gallery", "storage" -> {
                        if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)

                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }

                    }

                    "microphone" -> {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)
                    }

                    "location" -> {
                        if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)
                            permissionLauncher.launch(Manifest.permission.CONTROL_LOCATION_UPDATES)
                            permissionLauncher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)

                        } else {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                            permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)

                        }
                    }

                    "notifications" -> {
                        if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            success("Notifications permission not required for this SDK version".also { Napier.i(it) })
                        }
                    }

                    "calendar" -> {
                        permissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                        permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)

                    }
                }
*
*
* */

/*override fun requestSinglePermission(permission: String) {
       val permissionRequired = this.checkSelfPermission(permission) != PERMISSION_GRANTED
       if (permissionRequired) {
           val rationalePermission = this.shouldShowRequestPermissionRationale(permission)
           if (rationalePermission) {
               when (permission) {
                   "camera" -> {
                       permissionLauncher.launch(Manifest.permission.CAMERA)
                   }

                   "gallery", "storage" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {

                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)

                       } else {
                           permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                           permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                       }

                   }

                   "microphone" -> {
                       permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                       permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)
                   }

                   "location" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                           permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                           permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)
                           permissionLauncher.launch(Manifest.permission.CONTROL_LOCATION_UPDATES)
                           permissionLauncher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)

                       } else {
                           permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                           permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)

                       }
                   }

                   "notifications" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                           permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                       } else {
                           success("Notifications permission not required for this SDK version".also { Napier.i(it) })
                       }
                   }

                   "calendar" -> {
                       permissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                       permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)

                   }
               }
           } else {
               when (permission) {
                   "camera" -> {
                       permissionLauncher.launch(Manifest.permission.CAMERA)
                   }

                   "gallery", "storage" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                           permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)

                       } else {
                           permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                           permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                       }

                   }

                   "microphone" -> {
                       permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                       permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)
                   }

                   "location" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                           permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                           permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)
                           permissionLauncher.launch(Manifest.permission.CONTROL_LOCATION_UPDATES)
                           permissionLauncher.launch(Manifest.permission.NEARBY_WIFI_DEVICES)

                       } else {
                           permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                           permissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                           permissionLauncher.launch(Manifest.permission.ACCESS_MEDIA_LOCATION)

                       }
                   }

                   "notifications" -> {
                       if (SDK_INT >= VERSION_CODES.TIRAMISU) {
                           permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                       } else {
                           success("Notifications permission not required for this SDK version".also { Napier.i(it) })
                       }
                   }

                   "calendar" -> {
                       permissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                       permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)

                   }
               }
           }
       } else {
           // All permissions are already granted
           success("Already granted for $permission".also { Napier.i(it) })
       }
   }*/
