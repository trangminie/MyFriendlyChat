package com.udacity.myfriendlychat.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.udacity.myfriendlychat.ui.viewmodel.BaseViewModel

fun <T: BaseViewModel> FragmentActivity.injectVM(factory: ViewModelProvider.Factory, clazz: Class<T>) : T{
    return ViewModelProviders.of(this, factory).get(clazz)
}