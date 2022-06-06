package com.globant.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globant.data.database.dao.CharacterDao
import com.globant.data.database.entity.MarvelCharacterEntity

@Database(
    version = 1,
    entities = [
        MarvelCharacterEntity::class
    ]
)
abstract class MarvelDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "marvel_database"
        private lateinit var instance: MarvelDatabase

        @Synchronized
        fun getInstance(context: Context): MarvelDatabase {
            if (!this::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarvelDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

    }

    abstract fun characterDao(): CharacterDao

}