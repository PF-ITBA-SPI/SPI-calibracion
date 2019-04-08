package ar.edu.itba.spi.calibracion.api.models

import com.google.gson.annotations.SerializedName

class Building {
    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("floors")
    var floors: List<Floor>? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @SerializedName("zoom")
    var zoom: Double? = null

    @SerializedName("defaultFloorId")
    var defaultFloorId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Building

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id.hashCode()
    }

    override fun toString(): String {
        return "$name"
    }
}

