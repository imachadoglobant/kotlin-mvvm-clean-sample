package com.globant.data.mapper

import com.globant.data.database.entity.CharacterThumbnailEntity
import com.globant.data.database.entity.MarvelCharacterEntity
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.entities.MarvelCharacterThumbnail

class CharacterMapperLocal : BaseMapperRepository<MarvelCharacterEntity, MarvelCharacter> {

    override fun transform(type: MarvelCharacterEntity): MarvelCharacter = MarvelCharacter(
        id = type.id,
        name = type.name,
        description = type.description,
        thumbnail = MarvelCharacterThumbnail(
            type.thumbnail.path,
            type.thumbnail.extension
        )
    )

    override fun transformToRepository(type: MarvelCharacter): MarvelCharacterEntity =
        MarvelCharacterEntity(
            id = type.id,
            name = type.name,
            description = type.description,
            thumbnail = CharacterThumbnailEntity(
                path = type.thumbnail.path,
                extension = type.thumbnail.extension
            )
        )

}
