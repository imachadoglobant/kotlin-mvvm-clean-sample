package com.globant.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.globant.di.scopes.ViewModelKey
import com.globant.viewmodels.AppViewModelFactory
import com.globant.viewmodels.CharacterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    abstract fun characterViewModel(viewModel: CharacterViewModel): ViewModel

}