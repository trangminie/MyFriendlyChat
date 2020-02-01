package com.udacity.myfriendlychat.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.udacity.domain.entities.FriendlyMessage
import com.udacity.domain.interact.AuthenticationInteract
import com.udacity.domain.interact.ChatInteract
import com.udacity.myfriendlychat.FriendlyChatApp
import com.udacity.myfriendlychat.ui.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainVM constructor(
        application: FriendlyChatApp,
        private val authenticationInteract: AuthenticationInteract,
        private val chatInteract: ChatInteract
    ) : BaseViewModel(application) {

    val listMsgItem = MutableLiveData<List<FriendlyMessage>>()
    var msgItemLD = MutableLiveData<FriendlyMessage>()
    var msgLengthLD = MutableLiveData<Long>()
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mMessageDatabaseReference: DatabaseReference
    private var mChildEventListener: ChildEventListener? = null
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig


    fun initFirebaseDb() {
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mMessageDatabaseReference = mFirebaseDatabase.reference.child("messages")

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSetting =
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSetting)
        var defaultConfigMap = hashMapOf<String, Any>()
        defaultConfigMap.put(
            MainActivity.FRIENDLY_MESSAGE_LENGTH_KEY,
            MainActivity.DEFAULT_MSG_LENGTH_LIMIT
        )
        mFirebaseRemoteConfig.setDefaultsAsync(defaultConfigMap)

        fetchConfig()
    }

    fun attachChildEventListener() {
        if (mChildEventListener == null) {
            mChildEventListener = object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                override fun onChildRemoved(p0: DataSnapshot) {}

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val friendlyMessage = p0.getValue(FriendlyMessage::class.java)
                    if (friendlyMessage != null) chatInteract.pushMessage(friendlyMessage)
                }
            }
        }
        mMessageDatabaseReference.addChildEventListener(mChildEventListener!!)
    }

    fun detachChildEventListener() {
        if (mChildEventListener != null) {
            mMessageDatabaseReference.removeEventListener(mChildEventListener!!)
            mChildEventListener = null
        }
    }

    fun uploadPhoto(uri: Uri?, userName: String) {
        val task = chatInteract.uploadPhoto(uri.toString(), userName) as UploadTask
        task.addOnSuccessListener {
            val result = it.storage.downloadUrl
            result.addOnSuccessListener { uri ->
                val message = FriendlyMessage(
                    null,
                    userName,
                    uri.toString()
                )
                chatInteract.pushMessage(message)
            }

        }
    }

    private fun fetchConfig() {
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            msgLengthLD.postValue(mFirebaseRemoteConfig.getLong(MainActivity.FRIENDLY_MESSAGE_LENGTH_KEY))
        }
    }

    fun isCurrentUserLogin() : String? = authenticationInteract.checkAuthState()

}