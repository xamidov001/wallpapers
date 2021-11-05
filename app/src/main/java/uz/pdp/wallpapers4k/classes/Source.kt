package uz.pdp.wallpaperretro.classes

data class Source(
    val ancestry: Ancestry,
    val cover_photo: CoverPhoto,
    val description: String,
    val meta_description: String,
    val meta_title: String,
    val subtitle: String,
    val title: String
)