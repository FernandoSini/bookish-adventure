package br.com.flemis.bookishadventure.utils

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIDevice
import platform.darwin.NSObject
import platform.AppTrackingTransparency.ATTrackingManager
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusAuthorized
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusDenied
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusNotDetermined
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusRestricted

enum class IOSPermissionStatus {
    Granted,
    Denied,
    NotDetermined,
    Restricted
}

class IOSPermissionHandler : NSObject(), CLLocationManagerDelegateProtocol {
    private var locationManager: CLLocationManager = CLLocationManager()
    private var locationCallback: ((IOSPermissionStatus) -> Unit)? = null
    private var locationResultContinuation: (CancellableContinuation<Result<CLLocation>>)? = null

    init {
        locationManager.delegate = this
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.allowsBackgroundLocationUpdates = true
        locationManager.pausesLocationUpdatesAutomatically = false
    }

    suspend fun requestCurrentLocation(): Result<CLLocation> =
        suspendCancellableCoroutine { continuation ->
            locationResultContinuation = continuation
            locationManager.requestLocation()
        }

    fun requestPermission(permission: String, callback: (IOSPermissionStatus) -> Unit) = when (permission) {
        "camera" -> {
            val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
            when (status) {
                AVAuthorizationStatusAuthorized -> callback(IOSPermissionStatus.Granted)
                AVAuthorizationStatusDenied -> callback(IOSPermissionStatus.Denied)
                AVAuthorizationStatusNotDetermined -> {
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        callback(if (granted) IOSPermissionStatus.Granted else IOSPermissionStatus.Denied)
                    }
                }

                else -> callback(IOSPermissionStatus.Denied)
            }
        }

        "microphone" -> {
            val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeAudio)
            when (status) {
                AVAuthorizationStatusAuthorized -> callback(IOSPermissionStatus.Granted)
                AVAuthorizationStatusDenied -> callback(IOSPermissionStatus.Denied)
                AVAuthorizationStatusNotDetermined -> {
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeAudio) { granted ->
                        callback(if (granted) IOSPermissionStatus.Granted else IOSPermissionStatus.Denied)
                    }
                }

                else -> callback(IOSPermissionStatus.Denied)
            }
        }

        "gallery" -> {
            val status = PHPhotoLibrary.authorizationStatus()
            when (status) {
                PHAuthorizationStatusAuthorized -> callback(IOSPermissionStatus.Granted)
                PHAuthorizationStatusDenied -> callback(IOSPermissionStatus.Denied)
                PHAuthorizationStatusNotDetermined -> {
                    PHPhotoLibrary.requestAuthorization { newStatus ->
                        callback(
                            if (newStatus == PHAuthorizationStatusAuthorized) IOSPermissionStatus.Granted
                            else IOSPermissionStatus.Denied
                        )
                    }
                }

                else -> callback(IOSPermissionStatus.Denied)
            }
        }

        "location" -> {
            val status = CLLocationManager.authorizationStatus()
            when (status) {
                kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> callback(
                    IOSPermissionStatus.Granted
                )

                kCLAuthorizationStatusDenied -> callback(IOSPermissionStatus.Denied)
                kCLAuthorizationStatusNotDetermined -> {
                    callback(IOSPermissionStatus.NotDetermined)
                    locationManager.requestWhenInUseAuthorization()
                }

                else -> callback(IOSPermissionStatus.Denied)
            }
        }

        "tracking" -> {
            // Tracking permission is not directly available in iOS, but you can use AppTrackingTransparency framework
            // This is a placeholder for future implementation
            // App Tracking Transparency (ATT) permission
            if (UIDevice.currentDevice.systemVersion.substringBefore(".").toInt() >= 14) {
                ATTrackingManager.requestTrackingAuthorizationWithCompletionHandler { status ->
                     when (status) {
                        ATTrackingManagerAuthorizationStatusAuthorized -> callback(IOSPermissionStatus.Granted)
                        ATTrackingManagerAuthorizationStatusNotDetermined -> callback(IOSPermissionStatus.NotDetermined)
                        ATTrackingManagerAuthorizationStatusDenied -> callback(IOSPermissionStatus.Denied)
                        ATTrackingManagerAuthorizationStatusRestricted -> callback(IOSPermissionStatus.Restricted)
                        else -> callback(IOSPermissionStatus.NotDetermined)
                    }

                }
            } else {
                callback(IOSPermissionStatus.NotDetermined)
            }

        }

        else -> callback(IOSPermissionStatus.NotDetermined)
    }

    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        val result = when (didChangeAuthorizationStatus) {
            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> IOSPermissionStatus.Granted
            kCLAuthorizationStatusDenied -> IOSPermissionStatus.Denied
            kCLAuthorizationStatusNotDetermined -> IOSPermissionStatus.NotDetermined
            else -> IOSPermissionStatus.Denied
        }
        locationCallback?.invoke(result)
        locationCallback = null
    }

    fun getPermissionStatus(permission: String): IOSPermissionStatus {
        return when (permission) {
            "camera" -> when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
                AVAuthorizationStatusAuthorized -> return IOSPermissionStatus.Granted
                AVAuthorizationStatusDenied -> return IOSPermissionStatus.Denied
                AVAuthorizationStatusNotDetermined -> return IOSPermissionStatus.NotDetermined
                else -> return IOSPermissionStatus.Denied
            }

            "microphone" -> when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeAudio)) {
                AVAuthorizationStatusAuthorized -> return IOSPermissionStatus.Granted
                AVAuthorizationStatusDenied -> return IOSPermissionStatus.Denied
                AVAuthorizationStatusNotDetermined -> return IOSPermissionStatus.NotDetermined
                else -> return IOSPermissionStatus.Denied
            }

            "gallery" -> when (PHPhotoLibrary.authorizationStatus()) {
                PHAuthorizationStatusAuthorized -> return IOSPermissionStatus.Granted
                PHAuthorizationStatusDenied -> return IOSPermissionStatus.Denied
                PHAuthorizationStatusNotDetermined -> return IOSPermissionStatus.NotDetermined
                else -> return IOSPermissionStatus.Denied
            }

            "location" -> when (CLLocationManager.authorizationStatus()) {
                kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> return IOSPermissionStatus.Granted
                kCLAuthorizationStatusDenied -> return IOSPermissionStatus.Denied
                kCLAuthorizationStatusNotDetermined -> return IOSPermissionStatus.NotDetermined
                else -> return IOSPermissionStatus.Denied
            }

            else -> return IOSPermissionStatus.NotDetermined
        }
    }
}