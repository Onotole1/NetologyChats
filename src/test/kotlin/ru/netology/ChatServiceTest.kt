package ru.netology

import org.junit.Assert.*
import org.junit.Test
import ru.netology.domain.ChatNotFoundError
import ru.netology.domain.Message
import ru.netology.domain.MessagesNotFoundError

class ChatServiceTest {

    private val service = ChatService()

    @Test
    fun `create message then message saved`() {
        val userId = 100500L
        val message = "test message"
        val expected = Message(id = 0L, text = message, authorId = userId)

        val result = service.createMessage(userId, message)

        assertEquals(expected, result)
    }

    @Test(expected = ChatNotFoundError::class)
    fun `get messages from 0 chats expect chat not found error`() {
        service.getMessages(chatId = 0, offset = 0, startFrom = 0)
    }

    @Test(expected = MessagesNotFoundError::class)
    fun `get messages from empty chat expect message not found error`() {
        val userId = 100500L
        val message = "test message"

        service.createMessage(userId, message)
        service.getMessages(chatId = 0, offset = 0, startFrom = 0)
    }

    @Test
    fun `get messages from chat expect messages from chat`() {
        val userId = 100500L
        val message = "test message"
        val expected = List(5) {
            Message(it.toLong(), message + it, userId)
        }

        service.createMessage(userId, "${message}0")
        service.createMessage(userId, "${message}1")
        service.createMessage(userId, "${message}2")
        service.createMessage(userId, "${message}3")
        service.createMessage(userId, "${message}4")
        val result = service.getMessages(chatId = 0, offset = 5, startFrom = 0)

        assertEquals(expected, result)
    }
}