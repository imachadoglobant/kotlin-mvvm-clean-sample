package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepository

class GetCharacterByIdUseCase {
    lateinit var marvelCharacterRepository: MarvelCharacterRepository
    operator fun invoke(id: Int, getFromRemote: Boolean) = marvelCharacterRepository.getCharacterById(id, getFromRemote)
}
