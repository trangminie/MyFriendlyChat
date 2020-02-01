package com.udacity.myfriendlychat.di.module

import android.app.Application
import android.content.Context
import com.udacity.myfriendlychat.FriendlyChatApp
import com.udacity.myfriendlychat.ui.base.ViewTransaction
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApp(application : FriendlyChatApp) : Application = application

    @Provides
    fun provideContext(application: FriendlyChatApp) : Context = application.applicationContext

    @Provides
    fun provideViewTransaction() = ViewTransaction()
}