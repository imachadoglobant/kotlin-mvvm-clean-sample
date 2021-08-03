package com.globant.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.globant.data.database.CharacterDatabase
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.CharacterService
import com.globant.domain.usecases.GetCharacterByIdUseCase

class AppViewModelFactory : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == CharacterViewModel::class.java) {
            CharacterViewModel(GetCharacterByIdUseCase().apply {
                marvelCharacterRepository = MarvelCharacterRepositoryImpl(
                    CharacterService(),
                    CharacterDatabase()
                )
            }) as T
        } else {
            super.create(modelClass)
        }
    }
}