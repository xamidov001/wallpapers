package uz.pdp.wallpapers4k.retro

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.pdp.wallpapers4k.classes.UnSplash
import uz.pdp.wallpapers4k.classes.random.RandomClass

interface ApiService {

    @GET("search/photos/?client_id=ZT6Ky6zkqsOsv2lZOTeOuTXT99gcqDCsMC2-etkmOS0&per_page=13&")
    suspend fun getData(@Query("query") query: String, @Query("page") page: Int): Response<UnSplash>

    @GET("photos/random?client_id=ZT6Ky6zkqsOsv2lZOTeOuTXT99gcqDCsMC2-etkmOS0&count=60")
    suspend fun getRandom(@Query("page") page: Int): Response<List<RandomClass>>

}