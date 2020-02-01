package com.udacity.myfriendlychat.ui.base

import android.content.Intent

interface IViewTransaction {

    fun goBack() : Boolean

    fun openActivity(intent: Intent, enterAnim : Int, exitAnim : Int)

}