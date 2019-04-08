package ar.edu.itba.spi.calibracion.api.models

import com.google.gson.annotations.SerializedName

class Overlay {
    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @SerializedName("width")
    var width: Double? = null

    @SerializedName("bearing")
    var bearing: Double? = null

    @SerializedName("anchor_x")
    var anchorX: Double? = null

    @SerializedName("anchor_y")
    var anchorY: Double? = null

    override fun toString(): String {
        return "Overlay $_id"
    }
}
