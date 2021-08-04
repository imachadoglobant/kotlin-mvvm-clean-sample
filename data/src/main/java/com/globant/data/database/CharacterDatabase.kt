package com.globant.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globant.data.database.dao.CharacterDao
import com.globant.data.database.entity.MarvelCharacterRoom

@Database(entities = [MarvelCharacterRoom::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "character_database"
        private lateinit var instance: CharacterDatabase

        @Synchronized
        fun getInstance(context: Context): CharacterDatabase {
            if (!this::instance.isInitialized) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterDatabase::class.java,
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