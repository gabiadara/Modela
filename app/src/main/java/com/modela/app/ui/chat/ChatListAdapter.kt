package com.modela.app.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.ChatConversation
import com.modela.app.databinding.ItemChatConversationBinding
import com.modela.app.util.loadImage
import com.modela.app.util.toTimeAgo
import com.modela.app.util.gone
import com.modela.app.util.visible

class ChatListAdapter(
    private val conversations: List<ChatConversation>,
    private val onClick: (ChatConversation) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemChatConversationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemChatConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conv = conversations[position]
        holder.binding.tvName.text = conv.participantName
        holder.binding.tvLastMessage.text = conv.lastMessage
        holder.binding.tvTime.text = conv.timestamp.toTimeAgo()
        holder.binding.ivAvatar.loadImage(conv.participantAvatar)
        if (conv.unreadCount > 0) {
            holder.binding.tvUnreadCount.visible()
            holder.binding.tvUnreadCount.text = conv.unreadCount.toString()
        } else {
            holder.binding.tvUnreadCount.gone()
        }
        holder.binding.root.setOnClickListener { onClick(conv) }
    }

    override fun getItemCount() = conversations.size
}
