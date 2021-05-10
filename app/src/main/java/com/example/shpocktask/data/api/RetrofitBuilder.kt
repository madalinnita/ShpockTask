package com.example.shpocktask.data.api

import com.example.shpocktask.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private fun <T> createRetrofitService(
        clazz: Class<T>,
        baseUrl: String,
        needConnectionTimeout: Boolean = false,
        connectionTimeoutInSeconds: Long = 20,
        accessTokenHeaderNedeed: Boolean = false
    ): T {

        val httpClient = OkHttpClient.Builder()

        enableLogging(httpClient, BuildConfig.DEBUG)

        if (needConnectionTimeout) {
            httpClient.readTimeout(connectionTimeoutInSeconds, TimeUnit.SECONDS)
        }

        httpClient.addInterceptor(HeaderInterceptor(accessTokenHeaderNedeed))

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .build()

        return retrofit.create(clazz)
    }

    val shpockService: ApiService =
        createRetrofitService(clazz = ApiService::class.java, baseUrl = BuildConfig.SHPOCK_BASE_URL, accessTokenHeaderNedeed = false)

    private fun enableLogging(builder: OkHttpClient.Builder, enable: Boolean) {
        if (enable) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }
    }

    private class HeaderInterceptor(private val accessTokenHeaderNedeed: Boolean = false) :
        Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return proceedWithRequest(chain)
        }

        private fun proceedWithRequest(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            addHeaders(builder)
            return chain.proceed(builder.build())
        }

        private fun addHeaders(builder: Request.Builder) {
            if (accessTokenHeaderNedeed) {
                builder.addHeader(
                    HEADER_AUTHORIZATION,
                    HEADER_X_AUTHORIZATION_VALUE + BuildConfig.API_KEY
                )
            }
        }

        companion object {
            const val HEADER_AUTHORIZATION = "Authorization"
            const val HEADER_X_AUTHORIZATION_VALUE = "Bearer "
        }
    }
}