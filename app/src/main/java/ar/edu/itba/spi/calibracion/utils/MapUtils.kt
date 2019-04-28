package ar.edu.itba.spi.calibracion.utils

import android.graphics.Bitmap
import ar.edu.itba.spi.calibracion.api.models.Building
import ar.edu.itba.spi.calibracion.api.models.Overlay
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng

fun buildingLatLng(building: Building): LatLng {
    return LatLng(building.latitude!!, building.longitude!!)
}

fun gMapsGroundOverlayOptions(overlay: Overlay, image: Bitmap): GroundOverlayOptions {
    return GroundOverlayOptions()
            .position(LatLng(overlay.latitude!!, overlay.longitude!!), overlay.width!!.toFloat())
            .bearing(overlay.bearing!!.toFloat())
            .anchor(overlay.anchorX!!.toFloat(), overlay.anchorY!!.toFloat())
            .image(BitmapDescriptorFactory.fromBitmap(image))
}
