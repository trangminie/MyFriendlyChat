package com.udacity.domain.repository

import com.udacity.domain.entities.FriendlyMessage

interface ChatRepository {
    fun pushMessage(item : FriendlyMessage)
    fun uploadPhoto(uri : String, userName : String) : Any
}