package ru.netology.domain

data class Message(
    val id: Long,
    val text: String,
    val authorId: Long,
)