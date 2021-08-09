package com.globant.data.mapper

import com.globant.data.service.response.CharacterResponse
import com.globant.domain.entities.MarvelCharacter
import javax.inject.Inject

open class CharacterMapperService @Inject constructor() :
    BaseMapperRepository<CharacterResponse, MarvelCharacter> {

    override fun transform(type: CharacterResponse): MarvelCharacter =
        MarvelCharacter(
            type.id,
            type.name,
            type.description
        )

    override fun transformToRepository(type: MarvelCharacter): CharacterResponse =
        CharacterResponse(
            type.id,
            type.name,
            type.description
        )

}
