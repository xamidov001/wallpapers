package uz.pdp.wallpapers4k.room.database

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import uz.pdp.wallpapers4k.room.dao.SplashDao
import uz.pdp.wallpapers4k.room.entity.SplashEntity

@Database(entities = [SplashEntity::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract fun helper(): SplashDao

    companion object {

        private var myDatabase: MyDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabase {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(context, MyDatabase::class.java, "db")
                    .allowMainThreadQueries()
                    .build()
            }

            return myDatabase!!
        }
    }

}