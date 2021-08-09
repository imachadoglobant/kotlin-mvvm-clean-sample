package com.globant.data.service

import com.globant.data.MarvelRequestGenerator
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApi
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result
import java.io.IOException
import javax.inject.Inject

class CharacterService @Inject constructor(
        private val api: MarvelRequestGenerator,
        private val mapper: CharacterMapperService
) {

    fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
        try {
            val response = callResponse.execute()
            if (response.isSuccessful) {
                response.body()?.data?.characters?.firstOrNull()?.let {
                    mapper.transform(it)
                }?.let {
                    return Result.Success(it)
                }
            }
            return Result.Failure(Exception(response.message()))
        } catch (e: RuntimeException) {
            return Result.Failure(Exception("Bad request/response"))
        } catch (e: IOException) {
            return Result.Failure(Exception("Bad request/response"))
        }
    }

}
