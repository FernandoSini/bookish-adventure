package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTargetMarker
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.MapState
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.onMapUpdate
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.setMap
import br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets.delegate.rememberMkMapViewDelegate
import br.com.flemis.bookishadventure.utils.getLatitude
import br.com.flemis.bookishadventure.utils.getLongitude
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpan
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKFeatureVisibility
import platform.MapKit.MKImageryMapConfiguration
import platform.MapKit.MKMapCamera
import platform.MapKit.MKMapConfiguration
import platform.MapKit.MKMapElevationStyle
import platform.MapKit.MKMapElevationStyleRealistic
import platform.MapKit.MKMapTypeHybridFlyover
import platform.MapKit.MKMapTypeMutedStandard
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKUserLocationMeta
import platform.UIKit.NSDirectionalEdgeInsets
import platform.UIKit.NSDirectionalEdgeInsetsMake

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Retention(AnnotationRetention.BINARY)
@ComposableTargetMarker(description = "MapKit Map Composable")
@Target(
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER,
)
actual annotation class NativeMapComposable


@Composable
actual fun NativeMapWidgetV2(
    modifier: Modifier,
    contentPadding: PaddingValues,
    mapState: MapState,
    content: @Composable @NativeMapComposable () -> Unit

) {
    val mapView = rememberMkMapView()
    val mapDelegate = rememberMkMapViewDelegate {
        mapState.onMapUpdate()
    }

    AppleMap(
        modifier = modifier,
        contentPadding = contentPadding,
        mapView = mapView,
        mapDelegate = mapDelegate,
        content = content,

    )

    DisposableEffect(mapView, mapState) {
        mapState.setMap(mapView)
        onDispose {
            Napier.d { "Disposing NativeMap Widget" }
            mapState.setMap(null)
        }
    }
}

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun AppleMap(
    modifier: Modifier,
    contentPadding: PaddingValues,
    mapView: MKMapView,
    mapDelegate: MKMapViewDelegateProtocol,
    content: @Composable () -> Unit,

) {
    val layoutDirection = LocalLayoutDirection.current
    val directionalEdgeInsets = remember(layoutDirection, contentPadding) {
        NSDirectionalEdgeInsetsMake(
            top = contentPadding.calculateTopPadding().value.toDouble(),
            leading = contentPadding.calculateStartPadding(layoutDirection).value.toDouble(),
            bottom = contentPadding.calculateBottomPadding().value.toDouble(),
            trailing = contentPadding.calculateEndPadding(layoutDirection).value.toDouble()
        )
    }
    DisposableEffect( mapView) {
        onDispose {
            Napier.d { "MapKit disposed" }

        }

    }



    UIKitView(
        modifier = modifier,
        properties = UIKitInteropProperties(
            isNativeAccessibilityEnabled = true,
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        ),
        factory = {
            mapView.apply {

                setPreferredConfiguration(MKImageryMapConfiguration(MKMapElevationStyleRealistic))
                setDelegate(mapDelegate)
                setDirectionalLayoutMargins(directionalEdgeInsets)
                setZoomEnabled(true)
                setScrollEnabled(true)
                setPitchEnabled(true)
                setShowsCompass(true)
                setMapType(MKMapTypeMutedStandard)
                setCamera(MKMapCamera.camera())

                setShowsScale(true)
                setOpaque(true)
                setShowsBuildings(true)
                setShowsUserTrackingButton(true)
                setShowsUserLocation(true)
                setUserInteractionEnabled(true)
                setShowsTraffic(true)
                setMultipleTouchEnabled(true)
                setExclusiveTouch(true)
                setPitchButtonVisibility(MKFeatureVisibility.MKFeatureVisibilityAdaptive)
                setRegion(
                    MKCoordinateRegionMake(
                        CLLocationCoordinate2DMake(
                            CLLocationManager().getLatitude(),
                            CLLocationManager().getLongitude()
                        ),
                        span = MKCoordinateSpanMake(0.1, 0.1),
                    )
                )
            }


        },
        update = { currentMapView ->
            currentMapView.delegate = mapDelegate
            currentMapView.directionalLayoutMargins = directionalEdgeInsets


            Napier.d { "MapKit update" }
        },
        onRelease = { currentMapView ->
            currentMapView.delegate = null
            currentMapView.removeAnnotations(mapView.annotations)
            currentMapView.willRemoveSubview(mapView)
            currentMapView.removeFromSuperview()
            print("removendo dados")

        }
    )
    CompositionLocalProvider(LocalMapView provides mapView) {
        content()
    }
}