package ar.edu.itba.spi.calibracion.utils

import android.graphics.Bitmap
import ar.edu.itba.spi.calibracion.api.models.Building
import ar.edu.itba.spi.calibracion.api.models.Overlay
import ar.edu.itba.spi.calibracion.api.models.Sample
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


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

fun gMapsMarkerOptions(sample: Sample): MarkerOptions {
    return MarkerOptions()
            .position(LatLng(sample.latitude!!, sample.longitude!!))
            .visible(true)
            .draggable(false)
            .title("${sample.fingerprint.size} APs")
            .snippet(sampleSnippet(sample))
}

private fun sampleSnippet(sample: Sample): String {
    val result = StringBuilder()
    sample.fingerprint.entries.forEach { (key, value) ->
        result.append(key)
        var ssid = "?"
        if (sample.extraData.containsKey(key)) {
            val ssid2 = (sample.extraData[key] as Map<String, Any>)["SSID"]
            if (ssid2 != null && ssid2 != "") {
                ssid = ssid2 as String
            }
        }
        result.append(" (")
                .append(ssid)
                .append(") ")
                .append(value)
                .append("dBm")
                .append("\n")
    }
    return result.trimEnd('\n').toString()
}
