package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets


import br.com.flemis.bookishadventure.features.maps.domain.entities.LatLng
import br.com.flemis.bookishadventure.features.maps.domain.entities.data
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2D
import platform.MapKit.MKAnnotationProtocol
import platform.MapKit.MKAnnotationView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
//classe que vai criar os markers, entao precisamos do annotation
class NativeMapAnnotation(
    val identifier: String,
    private val position: LatLng,
    private val viewCreator: (MKAnnotationProtocol) -> MKAnnotationView,
    private val viewBinder: (MKAnnotationView) -> Unit
) : NSObject(), MKAnnotationProtocol {

    fun createView(): MKAnnotationView = viewCreator(this)
    fun bindView(view: MKAnnotationView) = viewBinder(view)
    override fun coordinate(): CValue<CLLocationCoordinate2D> = position.data
}