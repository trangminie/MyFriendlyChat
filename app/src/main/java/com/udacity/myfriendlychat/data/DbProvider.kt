package com.udacity.myfriendlychat.data

import javax.inject.Inject

class DbProvider @Inject constructor (val authenticationDb: AuthenticationDb, val chatDb: ChatDb)