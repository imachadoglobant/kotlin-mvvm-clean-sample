package com.globant.data.repositories

import com.globant.data.database.CharacterDatasource
import com.globant.data.service.CharacterService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(
    private val characterService: CharacterService,
    private val characterDatasource: CharacterDatasource
) : MarvelCharacterRepository {

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> {
        return if (getFromRemote) {
            val marvelCharacterResult = characterService.getCharacterById(id)
            if (marvelCharacterResult is Result.Success) {
                insertOrUpdateCharacter(marvelCharacterResult.data)
            }
            marvelCharacterResult
        } else {
            characterDatasource.findById(id)
        }
    }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatasource.insert(character)
    }

}
