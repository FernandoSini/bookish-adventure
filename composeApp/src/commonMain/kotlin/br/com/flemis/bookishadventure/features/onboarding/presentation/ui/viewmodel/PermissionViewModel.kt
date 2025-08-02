package br.com.flemis.bookishadventure.features.onboarding.presentation.ui.viewmodel

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.utils.PermissionHandler
import br.com.flemis.bookishadventure.utils.PermissionStatus
import br.com.flemis.bookishadventure.utils.Preferences
import br.com.flemis.bookishadventure.utils.exceptions.PermissionDeniedException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PermissionViewModel(
    //val permissionsController: PermissionsController,
    private val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {
    private val preferences: Preferences
    val isAllPermissionsGranted: MutableStateFlow<Boolean>

    init {
        preferences = Preferences()
        isAllPermissionsGranted =
            MutableStateFlow<Boolean>(preferences.getBoolean("isAllPermissionsGranted", false))
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
                        preferences.putBoolean("isAllPermissionsGranted", false)

                        var snackBarResult = snackbarHostState.showSnackbar(
                            "Permission Denied permanently",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            preferences.putBoolean("isAllPermissionsGranted", true)
                        }
                    } catch (deniedException: DeniedException) {
                        isAllPermissionsGranted.value = false;
                        preferences.putBoolean("isAllPermissionsGranted", false)

                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Permission Denied",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            preferences.putBoolean("isAllPermissionsGranted", true)
                        }


                    } catch (e: RequestCanceledException) {
                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Request canceled",

                            )

                    }
                    isAllPermissionsGranted.value = true;
                    preferences.putBoolean("isAllPermissionsGranted", true)
                } else {
                    snackbarHostState.showSnackbar("permission already granted")
                    isAllPermissionsGranted.value = true
                    preferences.putBoolean("isAllPermissionsGranted", true)
                    permissionsController.isPermissionGranted(permission)
                }


            }
        }
    }*/
    suspend fun requestPermissions(permissions: List<String>, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            permissions.forEachIndexed { index, permission ->
                val permissionVerified = PermissionHandler().verifyPermission(permission, {})
                try {
                    if (!permissionVerified) {
                        val permissionStatus = PermissionHandler().requestPermission(permission)
                        when (permissionStatus) {
                            PermissionStatus.Granted, PermissionStatus.PartiallyGranted -> {
                                if (index == (permissions.size - 2)) {
                                    isAllPermissionsGranted.value = true
                                    preferences.putBoolean("isAllPermissionsGranted", true)
                                    snackbarHostState.showSnackbar("All permissions granted")
                                }
                            }

                            PermissionStatus.NotDetermined -> {
                            }

                            PermissionStatus.Denied, PermissionStatus.PermanentlyDenied -> {
                                isAllPermissionsGranted.value = false
                                preferences.putBoolean("isAllPermissionsGranted", false)
                                var snackBarResult = snackbarHostState.showSnackbar(
                                    "Permission Denied: $permission",
                                    "open settings",
                                    duration = SnackbarDuration.Short
                                )
                                if (snackBarResult == SnackbarResult.ActionPerformed) {
                                    PermissionHandler().openAppSettings()
                                    isAllPermissionsGranted.value = true;
                                    preferences.putBoolean("isAllPermissionsGranted", true)
                                }
                            }
                        }

                    }
                } catch (e: PermissionDeniedException) {
                    var snackbarResult = snackbarHostState.showSnackbar(
                        "Permission Denied", "open settings", duration = SnackbarDuration.Short
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        PermissionHandler().openAppSettings()
                        isAllPermissionsGranted.value = true;
                        preferences.putBoolean("isAllPermissionsGranted", true)
                    }

                }
            }
        }
    }

    /*suspend fun requestPermissions(
        permissions: List<Permission>, snackbarHostState: SnackbarHostState
    ) {
        viewModelScope.launch {
            permissions.forEachIndexed { index, _ ->
                val permissionGranted =
                    permissionsController.isPermissionGranted(permissions.elementAt(index))
                if (!permissionGranted) {
                    try {
                        permissionsController.providePermission(permissions.elementAt(index))
                        if (index == (permissions.size - 1)) {
                            isAllPermissionsGranted.value = true
                            preferences.putBoolean("isAllPermissionsGranted", true)
                        }

                    } catch (deniedAlwaysException: DeniedAlwaysException) {
                        isAllPermissionsGranted.value = false;
                        preferences.putBoolean("isAllPermissionsGranted", false)

                        var snackBarResult = snackbarHostState.showSnackbar(
                            "${permissions.elementAt(index).delegate} Denied permanently",
                            "open settings",
                            duration = SnackbarDuration.Short
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            preferences.putBoolean("isAllPermissionsGranted", true)
                        }
                    } catch (deniedException: DeniedException) {
                        isAllPermissionsGranted.value = false;
                        preferences.putBoolean("isAllPermissionsGranted", false)

                        var snackbarResult = snackbarHostState.showSnackbar(
                            "Permission Denied", "open settings", duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            permissionsController.openAppSettings()
                            isAllPermissionsGranted.value = true;
                            preferences.putBoolean("isAllPermissionsGranted", true)
                        }
                    }

                }
            }

        }
    }*/


    /* when (permissionGranted) {
                   PermissionStatus.Granted, PermissionStatus.PartiallyGranted -> {
                       snackbarHostState.showSnackbar("Permission already granted: $permission")
                       if (index == (permissions.size-1)) {
                           isAllPermissionsGranted.value = true
                           preferences.putBoolean("isAllPermissionsGranted", true)
                       }

                   }

                   PermissionStatus.NotDetermined -> PermissionHandler().requestPermission(permission)
                   PermissionStatus.Denied, PermissionStatus.PermanentlyDenied -> {
                       isAllPermissionsGranted.value = false
                       preferences.putBoolean("isAllPermissionsGranted", false)
                       var snackBarResult = snackbarHostState.showSnackbar(
                           "${permissions.elementAt(index)} Denied",
                           "open settings",
                           duration = SnackbarDuration.Short
                       )
                       if (snackBarResult == SnackbarResult.ActionPerformed) {
                           PermissionHandler().openAppSettings()
                           isAllPermissionsGranted.value = true;
                           preferences.putBoolean("isAllPermissionsGranted", true)
                       }

                   }
               }*/
}