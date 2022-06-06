package com.globant.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.data.database.entity.MarvelCharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character WHERE id = :id")
    fun findById(id: Int): MarvelCharacterEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(character: MarvelCharacterEntity)

}