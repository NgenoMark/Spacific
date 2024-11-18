package com.example.spacific

data class Message(
    val sender: String,  // The sender of the message (e.g., "currentUser" or "simton")
    val content: String, // The content of the message
    val time: String     // The time when the message was sent
)
