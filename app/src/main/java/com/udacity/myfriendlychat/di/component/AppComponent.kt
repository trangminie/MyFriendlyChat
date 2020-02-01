package com.udacity.myfriendlychat.di.component

import com.udacity.myfriendlychat.FriendlyChatApp
import com.udacity.myfriendlychat.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    DataDbModule::class,
    DomainInteractModule::class,
    DomainRepositoryModule::class
])

@Singleton
interface AppComponent : AndroidInjector<FriendlyChatApp>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FriendlyChatApp>()
}