package com.globant.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globant.data.database.dao.CharacterDao
import com.globant.data.database.entity.MarvelCharacterRoom

@Database(entities = [MarvelCharacterRoom::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "character_database"

        @Volatile // All threads have immediate access to this property
        private var instance: CharacterDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CharacterDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

    }

    abstract fun characterDao(): CharacterDao

}