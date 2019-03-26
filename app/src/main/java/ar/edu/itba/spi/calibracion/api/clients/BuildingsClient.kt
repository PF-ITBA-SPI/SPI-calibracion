package ar.edu.itba.spi.calibracion.api.clients

import io.reactivex.Observable
import retrofit2.http.GET

interface BuildingsClient {
    @GET("/buildings")
    fun list(): Observable<String>
}
