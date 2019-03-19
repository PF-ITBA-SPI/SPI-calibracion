package ar.edu.itba.spi.calibracion.api

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.Key


const val API_BASE_URL = "https://pf-itba-spi.herokuapp.com"

/**
 * Lazily-initialized singleton of default HTTP client. **Use this instance whenever possible!**
 */
val httpClient: OkHttpClient by lazy {
    OkHttpClient.Builder().addInterceptor(JwtInterceptor()).build()
}

class JwtInterceptor : okhttp3.Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizedRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $jwt").build()
        return chain.proceed(authorizedRequest)
    }
}

val jwt: String by lazy {
    val key: Key = Keys.secretKeyFor(SignatureAlgorithm.RS256) // TODO NOW load private key here, this generates a keypair
    val jws = Jwts.builder() // (1)
            .setSubject("Bob")      // (2)
            .signWith(key)          // (3)
            .compact()             // (4)
    jws
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
