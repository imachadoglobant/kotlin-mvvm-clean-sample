package com.globant.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.data.DEFAULT_ID
import com.globant.data.EMPTY_STRING
import com.globant.domain.entities.MarvelCharacterThumbnail

@Entity(tableName = "character")
data class MarvelCharacterEntity(
    @PrimaryKey
    var id: Int = DEFAULT_ID,
    var name: String = EMPTY_STRING,
    var description: String = EMPTY_STRING,
    @Embedded(prefix = "thumbnail_")
    var thumbnail: CharacterThumbnailEntity
)
