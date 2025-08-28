package br.com.flemis.bookishadventure

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun createSimpleButton(text: String, onClick: () -> Unit): UIViewController
    fun createSwiftMap(latitude: Double, longitude: Double, zoom: Double): UIViewController
    fun updateMapView(viewController: UIViewController, newLatitude: Double, newLongitude: Double, newZoom: Double)
    fun releaseMapView(
        viewController: UIViewController,
        initialLatitude: Double = 0.0,
        initialLongitude: Double = 0.0,
        initialZoom: Double =0.0
    )

}