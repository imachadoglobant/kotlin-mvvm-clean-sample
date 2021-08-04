package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.data.service.CharacterService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(
    private val characterService: CharacterService,
    private val characterDatabase: CharacterDatabase
) : MarvelCharacterRepository {

    private val mapper = CharacterMapperLocal()

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> =
        if (getFromRemote) {
            val marvelCharacterResult = characterService.getCharacterById(id)
            if (marvelCharacterResult is Result.Success) {
                insertOrUpdateCharacter(marvelCharacterResult.data)
            }
            marvelCharacterResult
        } else {
            characterDatabase.characterDao().findById(id)?.let {
                return Result.Success(mapper.transform(it))
            } ?: Result.Failure(Exception("Character not found"))
        }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.characterDao().insert(mapper.transformToRepository(character))
    }

}
