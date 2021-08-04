package com.globant.viewmodels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class AppViewModelProvider(activity: Activity) : ViewModelProvider(
    (activity as ViewModelStoreOwner),
    AppViewModelFactory(activity.applicationContext)
)