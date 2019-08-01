package ar.edu.itba.spi.calibracion.api.models

import android.net.wifi.ScanResult
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

    constructor(buildingId: String?, floorId: String?, latitude: Double?, longitude: Double?, scanResults: List<ScanResult>) {
        this.buildingId = buildingId
        this.floorId = floorId
        this.latitude = latitude
        this.longitude = longitude
        this.fingerprint.putAll(wifiResultToMap(scanResults))
    }

    public fun wifiResultToMap(scanResult: List<ScanResult>): Map<String, Double> {
        val result = HashMap<String, Double>(scanResult.size)
        scanResult.forEach { t -> result[t.BSSID] = t.level.toDouble() }
        return result
    }

    override fun toString(): String {
        return "Sample for floor $floorId of building $buildingId"
    }
}

