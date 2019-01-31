package ar.edu.itba.spi.calibracion.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val API_BASE_URL = "https://pf-itba-spi.herokuapp.com"

/**
 * Lazily-initialized singleton of default HTTP client. **Use this instance whenever possible!**
 */
val httpClient: OkHttpClient by lazy {
    OkHttpClient.Builder().build()
}

/**
 * Default Retrofit builder. Uses [API_BASE_URL], [RxJava2CallAdapterFactory] to work with observables,
 * and [ScalarsConverterFactory] to work with scalars (plain strings, etc.) and [MoshiConverterFactory]
 * to work with JSON.  Also uses [the default HTTP client][httpClient].
 */
val defaultRetrofitBuilder: Retrofit.Builder by lazy {
    Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // Adapt calls to RXJava (observables, etc.)
            .addConverterFactory(ScalarsConverterFactory.create())      // Work with plain strings, ints, etc.
            .addConverterFactory(MoshiConverterFactory.create())        // Work with JSON
            .client(httpClient)
}

/**
 * Default Retrofit instance.  Uses defaults defined by [defaultRetrofitBuilder].
 *
 * @see defaultRetrofitBuilder
 */
val defaultRetrofitInstance: Retrofit by lazy {
    defaultRetrofitBuilder.build()
}
