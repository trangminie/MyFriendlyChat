package com.udacity.myfriendlychat.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.udacity.myfriendlychat.R
import com.udacity.myfriendlychat.di.injectVM
import com.udacity.myfriendlychat.ui.base.BaseView
import com.udacity.myfriendlychat.ui.base.IViewTransaction
import com.udacity.myfriendlychat.ui.base.ViewTransaction
import com.udacity.myfriendlychat.ui.viewmodel.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity<V: BaseViewModel, VMF : ViewModelProvider.Factory> : DaggerAppCompatActivity(), BaseView, IViewTransaction{

    private val TAG = BaseActivity::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: VMF

    @Inject
    lateinit var viewTransaction: ViewTransaction

    lateinit var viewModel : V

    private var containerId: Int = 0

    private var animEnterResId: Int = 0
    private var animExitResId: Int = 0

    protected abstract fun getViewModelType() : Class<V>?

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        extractData(intent)
        initViewModel()
    }


    private fun extractData(intent: Intent?){
        intent ?: return
        animEnterResId = intent.getIntExtra(ViewTransaction.KEY_ANIM_ENTER_RES_ID, 0)
        animExitResId = intent.getIntExtra(ViewTransaction.KEY_ANIM_EXIT_RES_ID, 0)
    }

    private fun initViewModel(){
        getViewModelType() ?: return

        if (::viewModelFactory.isInitialized){
            viewModel = injectVM(viewModelFactory, getViewModelType()!!)
        }

        if (::viewModel.isInitialized){

        }
    }

    override fun finish() {
        super.finish()
        if (animEnterResId != 0 && animExitResId != 0)
            overridePendingTransition(animEnterResId, animExitResId)
    }

    override fun goBack(): Boolean {
        onBackPressed()
        return true
    }

    override fun openActivity(intent: Intent, enterAnim: Int, exitAnim: Int) {
        viewTransaction.openActivity(this, intent, enterAnim, exitAnim)
    }

    override fun onBackPressed() {
        if (viewTransaction.goBack(this)) return
        super.onBackPressed()
    }

}