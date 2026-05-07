package com.modela.app.data.model

data class ChatConversation(
    val id: String = "",
    val participantName: String = "",
    val participantAvatar: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L,
    val unreadCount: Int = 0
)

data class ChatMessage(
    val id: String = "",
    val conversationId: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0L,
    val isSent: Boolean = true
)
