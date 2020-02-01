package com.udacity.myfriendlychat.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.udacity.myfriendlychat.data.AuthenticationDb
import javax.inject.Inject

class AuthenticationFirebaseDb @Inject constructor(private var firebaseAuth: FirebaseAuth) : AuthenticationDb{

    override fun getCurrentLoginState(): String? {
        return firebaseAuth.currentUser?.displayName ?: null
    }

}