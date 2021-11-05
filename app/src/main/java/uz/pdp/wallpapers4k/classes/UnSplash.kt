package uz.pdp.wallpapers4k.classes

import uz.pdp.wallpaperretro.classes.Result

data class UnSplash(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)