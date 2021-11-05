package uz.pdp.wallpapers4k.retro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    const val BASE_URL = "https://api.unsplash.com/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val appService = getRetrofit().create(ApiService::class.java)
}