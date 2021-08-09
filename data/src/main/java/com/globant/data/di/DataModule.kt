package com.globant.data.di

import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [DatabaseModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideRepository(impl: MarvelCharacterRepositoryImpl): MarvelCharacterRepository

}