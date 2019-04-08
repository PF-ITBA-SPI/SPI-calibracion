package ar.edu.itba.spi.calibracion.api.models

import com.google.gson.annotations.SerializedName

class Floor {
    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("number")
    val number: Integer? = null

    @SerializedName("overlay")
    val overlay: Overlay? = null

    override fun toString(): String {
        return "$name"
    }
}

