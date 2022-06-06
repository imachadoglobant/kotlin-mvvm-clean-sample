package com.globant.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.globant.data.database.CharacterDatasource
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.CharacterService
import com.globant.domain.usecases.GetCharacterByIdUseCase

class AppViewModelFactory(private val context: Context) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == CharacterViewModel::class.java) {
            CharacterViewModel(GetCharacterByIdUseCase().apply {
                marvelCharacterRepository = MarvelCharacterRepositoryImpl(
                    CharacterService(context),
                    CharacterDatasource(context)
                )
            }) as T
        } else {
            super.create(modelClass)
        }
    }

}