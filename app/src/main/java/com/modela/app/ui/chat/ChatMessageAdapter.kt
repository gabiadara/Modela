package com.modela.app.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.ChatMessage
import com.modela.app.databinding.ItemChatMessageReceivedBinding
import com.modela.app.databinding.ItemChatMessageSentBinding
import com.modela.app.util.toMessageTime

class ChatMessageAdapter(
    private val messages: List<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SENT = 0
        private const val TYPE_RECEIVED = 1
    }

    inner class SentViewHolder(val binding: ItemChatMessageSentBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ReceivedViewHolder(val binding: ItemChatMessageReceivedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int) =
        if (messages[position].isSent) TYPE_SENT else TYPE_RECEIVED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENT) {
            SentViewHolder(ItemChatMessageSentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            ReceivedViewHolder(ItemChatMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        when (holder) {
            is SentViewHolder -> {
                holder.binding.tvMessage.text = msg.text
                holder.binding.tvTime.text = msg.timestamp.toMessageTime()
            }
            is ReceivedViewHolder -> {
                holder.binding.tvMessage.text = msg.text
                holder.binding.tvTime.text = msg.timestamp.toMessageTime()
            }
        }
    }

    override fun getItemCount() = messages.size
}
