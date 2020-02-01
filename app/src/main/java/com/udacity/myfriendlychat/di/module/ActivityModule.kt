package com.udacity.myfriendlychat.di.module

import com.udacity.myfriendlychat.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun provideMainActivity() : MainActivity
}