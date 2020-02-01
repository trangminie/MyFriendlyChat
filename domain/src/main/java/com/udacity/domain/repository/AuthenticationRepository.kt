package com.udacity.domain.repository

interface AuthenticationRepository {
    fun getCurrentLoginState() : String?
}