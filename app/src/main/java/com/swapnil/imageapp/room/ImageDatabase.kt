package com.swapnil.imageapp.room


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.swapnil.imageapp.model.ImageData

/**
 *  [ImageDatabase]: This class is used to get instance of [ImageDatabase]
 */
@Database(entities = [ImageData::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}

private lateinit var INSTANCE: ImageDatabase
    fun getDatabase(context: Context): ImageDatabase {
        synchronized(ImageDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,
                    "image"
                ).build()
            }
        }
        return INSTANCE
    }

/**
 *  [ImageDao]: DAO class from Image database
 *              List of all queries to fetch data from local db
 */

@Dao
interface ImageDao {

    @Query("select * from IMAGES where date =:date")
    fun getImage(date: String): LiveData<ImageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageData(image: ImageData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateImageData(image: ImageData)
}
