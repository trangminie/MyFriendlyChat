package com.udacity.myfriendlychat.data.repository

import com.udacity.domain.repository.AuthenticationRepository
import com.udacity.myfriendlychat.data.DbProvider

class AuthenticationRepositotyImpl(private val dbProvider: DbProvider) : AuthenticationRepository{
    override fun getCurrentLoginState(): String? {
        return dbProvider.authenticationDb.getCurrentLoginState()
    }
}