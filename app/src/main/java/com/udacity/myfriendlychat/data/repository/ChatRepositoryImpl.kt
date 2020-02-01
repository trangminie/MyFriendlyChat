package com.udacity.myfriendlychat.data.repository

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.udacity.domain.entities.FriendlyMessage
import com.udacity.domain.repository.ChatRepository
import com.udacity.myfriendlychat.data.DbProvider

class ChatRepositoryImpl (private val dbProvider: DbProvider) : ChatRepository{

    override fun uploadPhoto(uri: String, userName: String) : UploadTask {
        return dbProvider.chatDb.uploadImage(Uri.parse(uri), userName)
    }

    override fun pushMessage(item: FriendlyMessage) {
        dbProvider.chatDb.pushMessage(item)
    }

}