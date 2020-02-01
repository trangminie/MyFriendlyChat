package com.udacity.myfriendlychat.data

import com.google.firebase.auth.FirebaseAuth

interface AuthenticationDb : InterfaceDb<FirebaseAuth>{
    fun getCurrentLoginState() : String?
}