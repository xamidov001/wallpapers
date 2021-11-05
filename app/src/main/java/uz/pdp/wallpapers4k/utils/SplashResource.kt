package uz.pdp.wallpapers4k.utils

import uz.pdp.wallpapers4k.classes.UnSplash

sealed class SplashResource {

    object Loading: SplashResource()

    class Success(val unsplash: UnSplash): SplashResource()

    class Error(val message: String): SplashResource()

}