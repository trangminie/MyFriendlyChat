package com.udacity.myfriendlychat.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.udacity.myfriendlychat.data.AuthenticationDb
import com.udacity.myfriendlychat.data.ChatDb
import com.udacity.myfriendlychat.data.DbProvider
import com.udacity.myfriendlychat.data.firebase.AuthenticationFirebaseDb
import com.udacity.myfriendlychat.data.firebase.ChatFirebaseDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataDbModule {
    @Singleton
    @Provides
    fun providerAuthenticationDb() : AuthenticationDb{
        return AuthenticationFirebaseDb(FirebaseAuth.getInstance())
    }

    @Singleton
    @Provides
    fun providerChatDb() : ChatDb{
        return ChatFirebaseDb(FirebaseStorage.getInstance(), FirebaseDatabase.getInstance())
    }

    @Singleton
    @Provides
    fun providerDbProvider(authenticationDb: AuthenticationDb, chatDb: ChatDb): DbProvider{
        return DbProvider(authenticationDb, chatDb)
    }
}