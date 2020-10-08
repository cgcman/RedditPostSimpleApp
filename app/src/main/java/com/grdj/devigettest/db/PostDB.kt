package com.grdj.devigettest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grdj.devigettest.domain.children.Data

@Database(entities = arrayOf(Data::class), version = 1, exportSchema = false)
abstract class PostDB: RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        private const val DATABASE_NAME = "devigetTest.db"
        private var instance: PostDB? = null
        private fun create(context: Context): PostDB =
            Room.databaseBuilder(context, PostDB::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        fun getInstance(context: Context): PostDB =
            (instance ?: create(context)).also { instance = it }
    }
}