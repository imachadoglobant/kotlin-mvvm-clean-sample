package com.globant.data.mapper

import com.globant.data.database.entity.MarvelCharacterRoom
import com.globant.domain.entities.MarvelCharacter

class CharacterMapperLocal : BaseMapperRepository<MarvelCharacterRoom, MarvelCharacter> {

    override fun transform(type: MarvelCharacterRoom): MarvelCharacter = MarvelCharacter(
            type.id,
            type.name,
            type.description
    )

    override fun transformToRepository(type: MarvelCharacter): MarvelCharacterRoom = MarvelCharacterRoom(
            type.id,
            type.name,
            type.description
    )

}
