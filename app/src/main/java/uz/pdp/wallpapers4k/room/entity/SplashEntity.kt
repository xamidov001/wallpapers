package uz.pdp.wallpapers4k.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SplashEntity(

    @PrimaryKey
    var id: String,
    var name: String,
    var url: String,
    var counts: Int,
    var width: Int,
    var height: Int

) : Serializable