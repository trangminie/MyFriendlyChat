package com.udacity.domain.interact

import com.udacity.domain.entities.FriendlyMessage

interface ChatInteract {
    fun pushMessage(item : FriendlyMessage)
    fun uploadPhoto(uri : String, userName : String) : Any
}