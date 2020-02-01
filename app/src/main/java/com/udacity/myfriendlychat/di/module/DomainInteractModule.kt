package com.udacity.myfriendlychat.di.module

import com.udacity.domain.interact.AuthenticationInteract
import com.udacity.domain.interact.AuthenticationInteractImpl
import com.udacity.domain.interact.ChatInteract
import com.udacity.domain.interact.ChatInteractImpl
import com.udacity.domain.repository.AuthenticationRepository
import com.udacity.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainInteractModule {

    @Singleton
    @Provides
    fun provideAuthenticationInteract(authenticationRepository: AuthenticationRepository) : AuthenticationInteract{
        return AuthenticationInteractImpl(authenticationRepository)
    }

    @Singleton
    @Provides
    fun provideChatInteract(chatRepository: ChatRepository): ChatInteract{
        return ChatInteractImpl(chatRepository)
    }
}