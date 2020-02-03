package com.test.testcatsapp.data

import android.content.Context
import androidx.room.*
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.Observable

@Database(
    entities = [Cat::class],
    version = 1,
    exportSchema = false
)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object {
        fun create(context: Context) =
            Room
                .databaseBuilder(context, CatsDatabase::class.java, "cats-db")
                .build()
    }
}

@Dao
interface CatsDao {
    @Query("select * from cats")
    fun all(): Observable<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cat: Cat)

    @Query("delete from cats where id = :id")
    fun deleteById(id: String)
}
