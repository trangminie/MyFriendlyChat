package com.udacity.myfriendlychat.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.myfriendlychat.ui.base.LoadingStatus

abstract class BaseViewModel (val app: Application) : AndroidViewModel(app){

    @JvmField
    val loadingLV = MutableLiveData<LoadingStatus>()
}