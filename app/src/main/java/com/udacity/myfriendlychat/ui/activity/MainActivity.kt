package com.udacity.myfriendlychat.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.udacity.domain.entities.FriendlyMessage
import com.udacity.myfriendlychat.R
import com.udacity.myfriendlychat.ui.adapter.MessageAdapter
import com.udacity.myfriendlychat.ui.viewmodel.MainVM
import com.udacity.myfriendlychat.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainVM, ViewModelFactory>() {
    companion object{
        const val ANONYMOUS = "anonymous"
        const val DEFAULT_MSG_LENGTH_LIMIT = 10
        const val FRIENDLY_MESSAGE_LENGTH_KEY = "frienly_msg_length"
        const val RC_SIGN_IN = 1
        const val RC_PHOTO_PICKER = 2

    }
    private var mUsername : String = ANONYMOUS
    private lateinit var mMessageAdapter : MessageAdapter

    override fun getViewModelType(): Class<MainVM>? {
        return MainVM::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        progressBar.visibility = View.VISIBLE
        mMessageAdapter = MessageAdapter( this)
        messageRv.adapter = mMessageAdapter
        messageRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        registerObserver()
        messageEditText.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendButton.isEnabled = p0.toString().trim().isNotEmpty()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        sendButton.setOnClickListener {
            val friendlyMessage = FriendlyMessage(
                messageEditText.text.toString(),
                mUsername,
                null
            )
            viewModel.pushMessage(friendlyMessage)
            messageEditText.setText("")
        }

        viewModel.initFirebaseDb()

        photoPickerButton.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun registerObserver(){
        viewModel.listMsgItem.observe(this, Observer {
            mMessageAdapter.updateList(it)
            progressBar.visibility = View.GONE
        })

        viewModel.msgItemLD.observe(this, Observer {
            mMessageAdapter.addMessage(it)
        })

        viewModel.msgLengthLD.observe(this, Observer {
            messageEditText.filters = arrayOf(InputFilter.LengthFilter(it.toInt()))
        })
    }

    override fun onStart() {
        super.onStart()
        val displayName = viewModel.isCurrentUserLogin()
        if (!displayName.isNullOrEmpty()){
            onSigninInitialize(displayName)
        } else {
            onSignoutCleanup()
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(
                    listOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build())
                ).build()
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.detachChildEventListener()
        mMessageAdapter.clearData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Cancel signin", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else if (requestCode == RC_PHOTO_PICKER && data != null){
            viewModel.uploadPhoto(data.data, mUsername)
        }
    }

    private fun onSigninInitialize(name: String?){
        mUsername = name ?: ANONYMOUS
        viewModel.attachChildEventListener()
    }

    private fun onSignoutCleanup(){
        mUsername = ANONYMOUS
        mMessageAdapter.clearData()
        viewModel.detachChildEventListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sign_out_menu -> {
                AuthUI.getInstance().signOut(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
