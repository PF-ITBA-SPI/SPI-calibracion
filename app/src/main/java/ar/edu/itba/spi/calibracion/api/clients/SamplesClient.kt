package ar.edu.itba.spi.calibracion.api.clients

import ar.edu.itba.spi.calibracion.api.models.Sample
import io.reactivex.Observable
import retrofit2.http.*

interface SamplesClient {
    @GET("/buildings/{buildingId}/samples")
    fun list(@Path("buildingId") buildingId: String): Observable<List<Sample>>

    @POST("/buildings/{buildingId}/samples")
    fun create(@Path("buildingId") id: String, @Body sample: Sample): Observable<Int>

    @DELETE("/buildings/{buildingId}/samples/{sampleId}")
    fun delete(@Path("buildingId") buildingId: String, @Path("sampleId") sampleId: String): Observable<Int>
}
