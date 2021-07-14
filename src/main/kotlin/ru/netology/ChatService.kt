package ru.netology

import ru.netology.domain.Chat
import ru.netology.domain.ChatNotFoundError
import ru.netology.domain.Message
import ru.netology.domain.MessagesNotFoundError

class ChatService {

    private val chats = mutableListOf<Chat>()
    private var messageId = 0L

    fun createMessage(userId: Long, message: String, chatId: Long = 0): Message {
        val chatIndex = chats.indexOfFirst { it.id == chatId }
            .takeIf { it >= 0 }
            ?: run {
                chats.add(Chat(chats.lastOrNull()?.id ?: 0L, emptyList()))
                chats.lastIndex
            }

        val currentChat = chats[chatIndex]
        val newMessage = Message(authorId = userId, id = messageId++, text = message)
        chats[chatIndex] = currentChat.copy(
            messages = currentChat.messages + newMessage
        )

        return newMessage
    }

    fun getMessages(chatId: Long, offset: Int, startFrom: Int): List<Message> =
        chats.singleOrNull { it.id == chatId }
            .let { it?.messages ?: throw ChatNotFoundError() }
            .asSequence()
            .drop(startFrom)
            .take(offset)
            .ifEmpty { throw MessagesNotFoundError() }
            .toList()
}
