package com.udacity.domain.interact

import com.udacity.domain.repository.AuthenticationRepository

class AuthenticationInteractImpl(private var authenticationRepository: AuthenticationRepository) : AuthenticationInteract{

    override fun checkAuthState() : String? {
        return authenticationRepository.getCurrentLoginState()
    }

}