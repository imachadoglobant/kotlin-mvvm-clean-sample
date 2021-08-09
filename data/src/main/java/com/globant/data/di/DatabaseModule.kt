package com.globant.data.di

import android.content.Context
import com.globant.data.database.CharacterDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) = CharacterDatabase(context)

}