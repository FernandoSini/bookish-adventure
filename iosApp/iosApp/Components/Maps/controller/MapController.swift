//
// Created by Fernando Fazio Sinigaglia on 06/08/25.
// Copyright (c) 2025 Flemis. All rights reserved.
//

import Foundation
import SwiftUI
import MapKit

class MapController: UIHostingController<SwiftMap> {


    init(latitude: Double, longitude: Double, zoom: Double) {
        //let mapView = SwiftMap(latitude: latitude, longitude: longitude, zoom: zoom)
       // super.init(rootView: mapView)
      super.init(rootView: SwiftMap(mapsViewModel:MapsViewModel(latitude: latitude, longitude: longitude, zoom: zoom)))
    }

    @available(*, unavailable)
    required dynamic init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }



    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)

    }



}

