package ar.edu.itba.spi.calibracion.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Sample: Serializable {
    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("buildingId")
    var buildingId: String? = null

    @SerializedName("floorId")
    var floorId: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @SerializedName("fingerprint")
    val fingerprint = HashMap<String, Double>()

    override fun toString(): String {
        return "Sample for floor $floorId of building $buildingId"
    }
}

