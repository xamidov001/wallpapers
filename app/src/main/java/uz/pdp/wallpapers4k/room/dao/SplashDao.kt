package uz.pdp.wallpapers4k.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import uz.pdp.wallpapers4k.room.entity.SplashEntity

@Dao
interface SplashDao {

    @Insert(onConflict = REPLACE)
    fun addSplash(splashEntity: SplashEntity)

    @Delete
    fun deleteSplash(splashEntity: SplashEntity)

    @Query("select * from SplashEntity where id=:n")
    fun isHave(n: String): SplashEntity

    @Query("select * from SplashEntity")
    fun getAll(): List<SplashEntity>
}