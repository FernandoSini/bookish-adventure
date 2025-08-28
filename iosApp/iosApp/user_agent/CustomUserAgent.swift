//
//  CustomUserAgent.swift
//  iosApp
//
//  Created by Fernando Fazio Sinigaglia on 05/05/25.
//  Copyright © 2025 Flemis. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI
import ComposeApp


/* class IosNativeViewFactory: NativeViewFactory {
    static var shared = IosNativeViewFactory()

    func getUserAgent() -> String {
        return CustomUserAgent().getString()
    }
} */
@objc class CustomUserAgent: NSObject {
    //eg. Darwin/16.3.0
    @objc func DarwinVersion() -> String {
        var sysinfo = utsname()
        uname(&sysinfo)
        let dv = String(
            bytes: Data(bytes: &sysinfo.release, count: Int(_SYS_NAMELEN)), encoding: .ascii)!
            .trimmingCharacters(in: .controlCharacters)
        return "Darwin/\(dv)"
    }

    //eg. CFNetwork/808.3
    @objc func CFNetworkVersion() -> String {
        let dictionary = Bundle(identifier: "com.apple.CFNetwork")?.infoDictionary!
        let version = dictionary?["CFBundleShortVersionString"] as! String
        return "CFNetwork/\(version)"
    }

    //eg. iOS/10_1
    @objc func deviceVersion() -> String {
        let currentDevice = UIDevice.current
        return "\(currentDevice.model)/\(currentDevice.systemName)/\(currentDevice.systemVersion)"
    }

    //eg. iPhone5,2
    @objc func deviceName() -> String {
        var sysinfo = utsname()
        uname(&sysinfo)
        return String(
            bytes: Data(bytes: &sysinfo.machine, count: Int(_SYS_NAMELEN)), encoding: .ascii)!
            .trimmingCharacters(in: .controlCharacters)
    }

    //eg. MyApp/1
    @objc func appNameAndVersion() -> String {
        guard let dictionary = Bundle.main.infoDictionary else {
            return ""
        }
        let version = dictionary["CFBundleShortVersionString"] as! String
        let name = dictionary["CFBundleName"] as! String
        return "\(name)/\(version)"
    }

    @objc func getString() -> String {
        return
            "User-Agent: \(appNameAndVersion()) \(deviceVersion()) \(deviceName()) \(CFNetworkVersion()) \(DarwinVersion())"
    }

    //print("the UA String is as \(UAString().addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)")
}

struct SimpleButton: View {
    var label: String
    var action: () -> Void
    var body: some View {
        Button(action: action) {
            Text(label)
                .padding()
                .background(Color.blue)
                .foregroundColor(.white)
                .cornerRadius(8)
        }
    }
}
