package com.udacity.myfriendlychat.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.domain.entities.FriendlyMessage
import com.udacity.myfriendlychat.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message.*

class MessageAdapter(val context: Context) : RecyclerView.Adapter<MessageAdapter.MsgViewHolder>(){
    private var msgList = ArrayList<FriendlyMessage>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val layoutInflater = LayoutInflater.from(context)
       return MsgViewHolder(layoutInflater.inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        holder.bindView(msgList[position])
    }

    override fun getItemCount(): Int = msgList.size

    fun updateList(list: List<FriendlyMessage>){
        msgList.clear()
        msgList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        msgList.clear()
        notifyDataSetChanged()
    }

    fun addMessage(item : FriendlyMessage){
        msgList.add(item)
        notifyItemInserted(msgList.size)
    }

    class MsgViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), LayoutContainer{

        override val containerView: View?
            get() = itemView

        fun bindView(item: FriendlyMessage){
            if (item.photoUrl != null){
                messageTextView.visibility = View.GONE
                photoImageView.visibility = View.VISIBLE
                Glide.with(photoImageView.context).load(item.photoUrl).into(photoImageView)
            } else {
                messageTextView.visibility = View.VISIBLE
                photoImageView.visibility = View.GONE
                messageTextView.text = item.text
            }
            nameTextView.text = item.name
        }
    }
}