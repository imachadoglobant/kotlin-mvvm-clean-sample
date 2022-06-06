package com.globant.data.database

import android.content.Context
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

class CharacterDatasource(context: Context) {

    private val marvelDatabase = MarvelDatabase.getInstance(context)
    private val mapper = CharacterMapperLocal()

    fun findById(id: Int): Result<MarvelCharacter> {
        return marvelDatabase.characterDao().findById(id)?.let {
            return Result.Success(mapper.transform(it))
        } ?: Result.Failure(Exception("Character not found"))
    }

    fun insert(character: MarvelCharacter) {
        marvelDatabase.characterDao().insert(mapper.transformToRepository(character))
    }

}