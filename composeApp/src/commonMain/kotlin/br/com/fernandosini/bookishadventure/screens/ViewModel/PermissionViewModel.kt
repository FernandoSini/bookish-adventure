package br.com.fernandosini.bookishadventure.screens.ViewModel

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

class PermissionViewModel(
    val permissionsController: PermissionsController,
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {


    val isAllPermissionsGranted: MutableStateFlow<Boolean>;
    private val settings: Settings;

    init {

        settings = Settings()
        isAllPermissionsGranted =
            MutableStateFlow<Boolean>(settings.getBoolean("isAllPermissionsGranted", false))
    }

    /*suspend fun requestPermissions(
        permissions: List<Permission>,
        snackbarHostState: SnackbarHostState
    ) {
        viewModelScope.launch {
            permissions.forEachIndexed { index, permission ->
                val permissionGranted = permissionsController.isPermissionGranted(permission)

                if (!permissionGranted) {

                    try {
                        permissionsController.providePermission(permission)


                    } catch (deniedAlwaysException: DeniedAlwaysException) {
                        isAllPermissionsGranted.value = false;
                        settings.putBoolean("isAllPermissionsGranted", false)

                        var snackBarResult = snackbarHostState.showSnackbar(
                            "Permission Denied permanently",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            settings.putBoolean("isAllPermissionsGranted", true)
                        }
                    } catch (deniedException: DeniedException) {
                        isAllPermissionsGranted.value = false;
                        settings.putBoolean("isAllPermissionsGranted", false)

                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Permission Denied",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            settings.putBoolean("isAllPermissionsGranted", true)
                        }


                    } catch (e: RequestCanceledException) {
                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Request canceled",

                            )

                    }
                    isAllPermissionsGranted.value = true;
                    settings.putBoolean("isAllPermissionsGranted", true)
                } else {
                    snackbarHostState.showSnackbar("permission already granted")
                    isAllPermissionsGranted.value = true
                    settings.putBoolean("isAllPermissionsGranted", true)
                    permissionsController.isPermissionGranted(permission)
                }


            }
        }
    }*/

    suspend fun requestPermissions(
        permissions: List<Permission>,
        snackbarHostState: SnackbarHostState
    ) {
        viewModelScope.launch {
            permissions.forEachIndexed { index, _ ->
                val permissionGranted =
                    permissionsController.isPermissionGranted(permissions.elementAt(index))
                if (!permissionGranted) {
                    try {
                        permissionsController.providePermission(permissions.elementAt(index))
                        if(index == (permissions.size - 1)){
                            isAllPermissionsGranted.value = true
                            settings.putBoolean("isAllPermissionsGranted", true)
                        }

                    } catch (deniedAlwaysException: DeniedAlwaysException) {
                        isAllPermissionsGranted.value = false;
                        settings.putBoolean("isAllPermissionsGranted", false)

                        var snackBarResult = snackbarHostState.showSnackbar(
                            "${permissions.elementAt(index).name} Denied permanently",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            settings.putBoolean("isAllPermissionsGranted", true)
                        }
                    } catch (deniedException: DeniedException) {
                        isAllPermissionsGranted.value = false;
                        settings.putBoolean("isAllPermissionsGranted", false)

                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Permission Denied",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            settings.putBoolean("isAllPermissionsGranted", true)
                        }
                    }

                }
            }

        }
    }
}