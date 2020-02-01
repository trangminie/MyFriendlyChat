package com.udacity.myfriendlychat

import com.udacity.myfriendlychat.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication

class FriendlyChatApp : DaggerApplication(), HasActivityInjector{

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}