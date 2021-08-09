package com.globant.di

import com.globant.activities.MainActivity
import com.globant.di.scopes.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity

}
