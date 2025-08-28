//
// Created by Fernando Fazio Sinigaglia on 02/08/25.
//

import Foundation
import ComposeApp
import SwiftUI
import MapKit


class IosNativeViewFactory: NativeViewFactory {
    func updateMapView(viewController: UIViewController, newLatitude: Double, newLongitude: Double, newZoom: Double) {
        guard let hostingController = viewController as? UIHostingController<SwiftMap>,
              let swiftMapView = hostingController.rootView as? SwiftMap
        else {
            return
        }


        //swiftMapView.updateRegion(latitude: newLatitude, longitude: newLongitude, zoom: newZoom)

    }


    static var shared = IosNativeViewFactory()

    func createSimpleButton(
        text: String,
        onClick: @escaping () -> Void
    ) -> UIViewController {
        let view = SimpleButton(label: text, action: onClick)
        return UIHostingController(rootView: view)
    }

    func createSwiftMap(latitude: Double, longitude: Double, zoom: Double) -> UIViewController {
        let view = SwiftMap(mapsViewModel: MapsViewModel(latitude: latitude, longitude: longitude, zoom: zoom))
        return UIHostingController(rootView: view)
    }

    func releaseMapView(viewController: UIViewController, initialLatitude: Double,
                        initialLongitude: Double,
                        initialZoom: Double) {
        // No specific release logic needed for SwiftUI views
        if let hostingController = viewController as? UIHostingController<SwiftMap> {
print("Releasing map view with initial coordinates: \(initialLatitude), \(initialLongitude), zoom: \(initialZoom)")
            hostingController.rootView.viewModel.updateRegion(latitude: initialLatitude, longitude: initialLongitude, zoom: initialZoom)

        }
    }


}
