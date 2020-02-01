package com.udacity.myfriendlychat.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.domain.interact.AuthenticationInteract
import com.udacity.domain.interact.ChatInteract
import com.udacity.domain.repository.AuthenticationRepository
import com.udacity.myfriendlychat.FriendlyChatApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(application: FriendlyChatApp,
                                           authenticationInteract: AuthenticationInteract,
                                           chatInteract: ChatInteract
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return genVMMap.getValue(modelClass).invoke() as T
    }

    private val genVMMap: Map<Class<*>, (() -> Any)> = mapOf(
        MainVM::class.java to {MainVM(application, authenticationInteract, chatInteract)}
    )

}