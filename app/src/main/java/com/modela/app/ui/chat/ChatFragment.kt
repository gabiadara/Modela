package com.modela.app.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.data.model.ChatMessage
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatMessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val conversationId = arguments?.getString("conversationId") ?: ""
        val userName = arguments?.getString("userName") ?: ""

        binding.tvUserName.text = userName
        messages.addAll(MockDataProvider.getMessages(conversationId))
        adapter = ChatMessageAdapter(messages)
        binding.rvMessages.adapter = adapter
        binding.rvMessages.scrollToPosition(messages.size - 1)

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                val msg = ChatMessage(
                    id = "m${messages.size + 1}",
                    conversationId = conversationId,
                    senderId = "me",
                    text = text,
                    timestamp = System.currentTimeMillis(),
                    isSent = true
                )
                messages.add(msg)
                adapter.notifyItemInserted(messages.size - 1)
                binding.rvMessages.scrollToPosition(messages.size - 1)
                binding.etMessage.text.clear()
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
