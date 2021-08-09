package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
        private val marvelCharacterRepository: MarvelCharacterRepository
) {

    operator fun invoke(id: Int, getFromRemote: Boolean) =
            marvelCharacterRepository.getCharacterById(id, getFromRemote)

}
