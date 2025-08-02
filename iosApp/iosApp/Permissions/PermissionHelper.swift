//
// Created by Fernando Fazio Sinigaglia on 28/06/25.
// Copyright (c) 2025 Flemis. All rights reserved.
//

import Foundation
import AVFoundation
import CoreLocation
import UIKit
import EventKit

@objc public enum IOSPermissionResult: Int {
    case granted
    case denied
    case notDetermined
    case restricted

}

@objc public class IOSPermissionHelper: NSObject, CLLocationManagerDelegate, ObservableObject {

    private var locationManager: CLLocationManager?
    private var locationCallback: ((IOSPermissionResult) -> Void)?

    @objc public func requestPermissions(_ permissions: [String], callback: @escaping (IOSPermissionResult) -> Void) {
        for permission in permissions {
            switch permission {
            case "calendar":
                let status = EKEventStore.authorizationStatus(for: .event)
                switch status {
                case .authorized:
                    callback(.granted)
                case .denied:
                    callback(.denied)
                case .notDetermined:
                    let eventStore = EKEventStore()
                    eventStore.requestAccess(to: .event) { granted, _ in
                        DispatchQueue.main.async {
                            callback(granted ? .granted : .denied)
                        }
                    }
                @unknown default:
                    callback(.denied)
                }
            case "camera":
                let status = AVCaptureDevice.authorizationStatus(for: .video)
                switch status {
                case .authorized:
                    callback(.granted)
                case .denied:
                    callback(.denied)
                case .notDetermined:
                    AVCaptureDevice.requestAccess(for: .video) { granted in
                        callback(granted ? .granted : .denied)
                    }
                @unknown default:
                    callback(.denied)
                }
            case "location":
                let status = CLLocationManager.authorizationStatus()
                switch status {
                case .authorizedAlways, .authorizedWhenInUse, .authorized:
                    callback(.granted)
                case .denied:
                    callback(.denied)
                case .notDetermined:
                    if self.locationManager == nil {
                        self.locationManager = CLLocationManager()
                        self.locationManager?.delegate = self
                    }
                    self.locationCallback = callback
                    self.locationManager?.requestWhenInUseAuthorization()

                @unknown default:
                    callback(.denied)
                }
            case "microphone":
                let status = AVCaptureDevice.authorizationStatus(for: .audio)
                switch status {
                case .authorized:
                    callback(.granted)
                case .denied:
                    callback(.denied)
                case .notDetermined:
                    AVCaptureDevice.requestAccess(for: .audio) { granted in
                        callback(granted ? .granted : .denied)
                    }
                @unknown default:
                    callback(.denied)
                }
            default:
                callback(.notDetermined)
                print("Unsupported permission: \(permission)")
            }
        }
    }

}