package com.globant.data.mapper

import com.globant.data.service.response.CharacterResponse
import com.globant.data.service.response.ThumbnailResponse
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.entities.MarvelCharacterThumbnail

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, MarvelCharacter> {

    override fun transform(type: CharacterResponse): MarvelCharacter =
        MarvelCharacter(
            id = type.id,
            name = type.name,
            description = type.description,
            thumbnail = MarvelCharacterThumbnail(
                path = type.thumbnail.path,
                extension = type.thumbnail.extension
            )
        )

    override fun transformToRepository(type: MarvelCharacter): CharacterResponse =
        CharacterResponse(
            id = type.id,
            name = type.name,
            description = type.description,
            thumbnail = ThumbnailResponse(
                path = type.thumbnail.path,
                extension = type.thumbnail.extension
            )
        )

}
