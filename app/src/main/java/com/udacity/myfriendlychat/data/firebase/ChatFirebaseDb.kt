package com.udacity.myfriendlychat.data.firebase

import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.udacity.domain.entities.FriendlyMessage
import com.udacity.myfriendlychat.data.ChatDb
import javax.inject.Inject

class ChatFirebaseDb @Inject constructor(firebaseStorage : FirebaseStorage, firebaseDatabase: FirebaseDatabase) : ChatDb {
    private var  mStorageReference: StorageReference = firebaseStorage.reference.child("chat_photos")
    private var mMessageDatabaseReference = firebaseDatabase.reference.child("messages")

    override fun uploadImage(uri: Uri, userName: String) : UploadTask {
        var lastPath = uri.lastPathSegment!!
        lastPath = lastPath.substring(lastPath.lastIndexOf('/') + 1)
        var photoReference = mStorageReference.child(lastPath)
        return photoReference.putFile(uri)
    }

    override fun pushMessage(item: FriendlyMessage) {
        mMessageDatabaseReference.push().setValue(item)
    }

}