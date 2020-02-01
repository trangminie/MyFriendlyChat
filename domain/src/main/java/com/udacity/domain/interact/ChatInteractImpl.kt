package com.udacity.domain.interact

import com.udacity.domain.entities.FriendlyMessage
import com.udacity.domain.repository.ChatRepository

class ChatInteractImpl(private var chatRepository: ChatRepository) : ChatInteract{
    override fun uploadPhoto(uri: String, userName: String) : Any {
        return chatRepository.uploadPhoto(uri, userName)
    }

    override fun pushMessage(item: FriendlyMessage) {
        chatRepository.pushMessage(item)
    }

}