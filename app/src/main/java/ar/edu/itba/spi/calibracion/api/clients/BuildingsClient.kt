package ar.edu.itba.spi.calibracion.api.clients

import ar.edu.itba.spi.calibracion.api.models.Building
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface BuildingsClient {
    @GET("/buildings")
    fun list(): Observable<List<Building>>

    @GET("/buildings/{id}")
    fun get(@Path("id") id: String): Observable<Building>
}
