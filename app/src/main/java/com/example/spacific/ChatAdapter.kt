package com.example.spacific

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    // ViewHolder class that holds the views for each message item
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.messageText)
        val messageTime: TextView = view.findViewById(R.id.messageTime)
        val messageImage: ImageView = view.findViewById(R.id.messageImage)
    }

    // Create a new view holder when a new message item is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // Inflate the layout depending on the message sender
        val view: View = if (viewType == 1) {
            // Layout for the current user's message (sent on the right)
            LayoutInflater.from(parent.context).inflate(R.layout.item_message_current_user, parent, false)
        } else {
            // Layout for Simton's message (sent on the left)
            LayoutInflater.from(parent.context).inflate(R.layout.item_message_simton, parent, false)
        }
        return MessageViewHolder(view)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.messageText.text = message.content
        holder.messageTime.text = message.time

        // Set the sender's profile picture based on the sender
        if (message.sender == "currentUser") {
            holder.messageImage.setImageResource(R.drawable.app) // Set current user's profile image
        } else {
            holder.messageImage.setImageResource(R.drawable.simton) // Set Simton's profile image
        }
    }

    // Get the type of view (for deciding which layout to use)
    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].sender == "currentUser") 1 else 2
    }

    // Return the number of messages
    override fun getItemCount(): Int {
        return messageList.size
    }
}
