package com.udacity.myfriendlychat.data

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.udacity.domain.entities.FriendlyMessage


interface ChatDb {
    fun uploadImage(uri: Uri, userName : String) : UploadTask
    fun pushMessage(item : FriendlyMessage)
}