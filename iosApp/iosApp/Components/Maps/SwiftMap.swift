//
// Created by Fernando Fazio Sinigaglia on 05/08/25.
//

import Foundation
import MapKit
import SwiftUI

struct SwiftMap: View {

    @ObservedObject private(set) var viewModel: MapsViewModel

    init(mapsViewModel: MapsViewModel) {
        viewModel = mapsViewModel
    }

    var body: some View {
        Map(coordinateRegion: $viewModel.region)
            .edgesIgnoringSafeArea(.all)
    }


}

