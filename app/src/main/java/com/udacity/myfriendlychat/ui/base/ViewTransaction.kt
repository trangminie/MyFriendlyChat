package com.udacity.myfriendlychat.ui.base

import android.app.ActivityOptions
import android.content.Intent
import android.text.BoringLayout
import androidx.fragment.app.FragmentActivity
import java.lang.Exception

class ViewTransaction {
    private var containerId: Int = 0

    companion object {
        const val KEY_ANIM_ENTER_RES_ID = "KEY_ANIM_ENTER_RES_ID"
        const val KEY_ANIM_EXIT_RES_ID = "KEY_ANIM_EXIT_RES_ID"
    }

    fun goBack(activity : FragmentActivity?) : Boolean{
        activity ?: return false
        val fm = activity.supportFragmentManager

        if (fm == null || fm.backStackEntryCount == 0){
            activity.finish()
            return false
        }

        fm.popBackStack()
        val transaction = fm.beginTransaction()
        val currentFrag = fm.findFragmentById(containerId)
        if (currentFrag != null){
            transaction.remove(currentFrag)
            transaction.commitAllowingStateLoss()
        }
        return true
    }

    fun openActivity(activity: FragmentActivity?, intent: Intent?, enterAnim: Int, exitAnim: Int){
        if (activity == null || intent == null) return
        try {
            val bundle = ActivityOptions.makeCustomAnimation(activity, enterAnim, exitAnim).toBundle()
            intent.putExtra(KEY_ANIM_ENTER_RES_ID, enterAnim)
            intent.putExtra(KEY_ANIM_EXIT_RES_ID, exitAnim)
            activity.startActivity(intent, bundle)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

}