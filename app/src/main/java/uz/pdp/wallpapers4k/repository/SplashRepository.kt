package uz.pdp.wallpapers4k.repository

import kotlinx.coroutines.flow.flow
import uz.pdp.wallpapers4k.retro.ApiService

class SplashRepository(private val apiService: ApiService) {

    fun getSplash(query: String, page: Int) = flow { emit(apiService.getData(query, page)) }

    fun getSplashRandom(page: Int) = flow { emit(apiService.getRandom(page)) }
}