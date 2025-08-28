//
// Created by Fernando Fazio Sinigaglia on 06/08/25.
// Copyright (c) 2025 Flemis. All rights reserved.
//

import Foundation
import SwiftUI
import MapKit

class MapsViewModel: ObservableObject {

    @State var region: MKCoordinateRegion
    let locationManager = CLLocationManager()
    @State private var route: MKRoute?
    //@ObservedObject private var viewModel = MapsViewModel()
    init(latitude: Double, longitude: Double, zoom: Double) {
        let span = MKCoordinateSpan(latitudeDelta: 0.01 * pow(2, zoom), longitudeDelta: 0.01 * pow(2, zoom))
        region = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: latitude, longitude: longitude), span: span)

    }

    func updateRegion(latitude: Double, longitude: Double, zoom: Double) {
        let span = MKCoordinateSpan(latitudeDelta: 0.01 * pow(2, zoom), longitudeDelta: 0.01 * pow(2, zoom))
        region = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: latitude, longitude: longitude), span: span)
    }

    func getUserLocation() async -> CLLocationCoordinate2D? {
        if #available(iOS 18, *) {
            let updates = await CLLocationUpdate.liveUpdates()
            do {
                let update = try await updates.first(where: { $0.location?.coordinate != nil })
                return update?.location?.coordinate
            } catch {
                print("Error getting user location: \(error)")

            }
        }
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.allowsBackgroundLocationUpdates = true
        locationManager.startUpdatingLocation()

        return locationManager.location?.coordinate


    }


    func getDirections(toDestination: CLLocationCoordinate2D) {
        Task {
            guard let userLocation = region.center as CLLocationCoordinate2D? else {
                return
            }
            let request = MKDirections.Request()
            request.source = MKMapItem(placemark: MKPlacemark(coordinate: userLocation))
            request.destination = MKMapItem(placemark: MKPlacemark(coordinate: toDestination))
            request.transportType = .automobile

            do {
                let directions = try await MKDirections(request: request).calculate()
                route = directions.routes.first

            } catch (let error) {
                print("Error getting directions: \(error)")

            }
        }
    }
    deinit {
        locationManager.stopUpdatingLocation()
        region.center = CLLocationCoordinate2D(latitude: 0, longitude: 0)
        region.span = MKCoordinateSpan(latitudeDelta: 0, longitudeDelta: 0)
    }


}