package com.udacity.myfriendlychat.di.module

import com.udacity.domain.repository.AuthenticationRepository
import com.udacity.domain.repository.ChatRepository
import com.udacity.myfriendlychat.data.DbProvider
import com.udacity.myfriendlychat.data.repository.AuthenticationRepositotyImpl
import com.udacity.myfriendlychat.data.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainRepositoryModule{

    @Singleton
    @Provides
    fun providerAuthenticationRepository(dbProvider: DbProvider): AuthenticationRepository{
        return AuthenticationRepositotyImpl(dbProvider)
    }

    @Singleton
    @Provides
    fun providerChatRepository(dbProvider: DbProvider) : ChatRepository{
        return ChatRepositoryImpl(dbProvider)
    }
}