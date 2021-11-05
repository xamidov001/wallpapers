package uz.pdp.wallpapers4k.utils

import androidx.paging.PagingData
import uz.pdp.wallpapers4k.classes.UnSplash
import uz.pdp.wallpapers4k.classes.random.RandomClass

sealed class SplashResourceRandom {

    object Loading: SplashResourceRandom()

    class Success(val list: PagingData<RandomClass>): SplashResourceRandom()

    class Error(val message: String): SplashResourceRandom()

}