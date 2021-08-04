package com.globant.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.data.DEFAULT_ID
import com.globant.data.EMPTY_STRING

@Entity(tableName = "character")
data class MarvelCharacterRoom(
        @PrimaryKey
        var id: Int = DEFAULT_ID,
        var name: String = EMPTY_STRING,
        var description: String = EMPTY_STRING
)
