package com.example.spacific

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacific.ui.theme.ComposeTutorialTheme

// Main activity class
class BabadogochatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                BabadogoChatScreen()
            }
        }
    }
}

// Data class for chat messages
data class BabadogoChatMessage(val author: String, val body: String)

// Chat screen composable
@Composable
fun BabadogoChatScreen() {
    var messages by remember {
        mutableStateOf(
            mutableListOf(
                BabadogoChatMessage(
                    "BDogo",
                    """
                    Welcome to Babadogo Chat! Here are the details you can ask about:
                    
                    • Cost
                    • Location
                    • Capacity
                    • Duration
                    • Discounts
                    
                    Please type your query from the above in lowercase to get a response.
                    """.trimIndent()
                )
            )
        )
    }
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display chat conversation
        BabadogoConversation(
            messages = messages,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                placeholder = { Text("Enter your message") }
            )

            Button(
                onClick = {
                    if (textState.text.isNotEmpty()) {
                        val userMessage = textState.text.trim()
                        messages.add(BabadogoChatMessage("Mark", userMessage))
                        textState = TextFieldValue("") // Clear input field

                        // Automated response logic
                        val response = getAutomatedResponse(userMessage)
                        if (response != null) {
                            messages.add(BabadogoChatMessage("BDogo", response))
                        }
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}

// Automated response logic
fun getAutomatedResponse(input: String): String? {
    return when {
        "cost" in input.lowercase() -> "The cost of the warehouse storage is $500 per month."
        "location" in input.lowercase() -> "The warehouse is located in Nairobi, Kenya."
        "capacity" in input.lowercase() -> "The warehouse can hold up to 10,000 units of goods."
        "duration" in input.lowercase() -> "The minimum duration for leasing is 3 months."
        "discounts" in input.lowercase() -> "We offer a 10% discount for leases longer than 1 year."
        else -> "I'm sorry, I didn't understand that. Please provide details like cost, location, capacity, duration, or discounts."
    }
}

// Composable to display the chat conversation
@Composable
fun BabadogoConversation(messages: List<BabadogoChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            BabadogoMessageCard(message)
        }
    }
}

// Composable to display an individual chat message
@Composable
fun BabadogoMessageCard(msg: BabadogoChatMessage) {
    val surfaceColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary
    )

    val isFromUser = msg.author == "Mark"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isFromUser) Arrangement.Start else Arrangement.End
    ) {
        if (isFromUser) {
            Image(
                painter = painterResource(R.drawable.profile_picture), // Replace with actual drawable
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                textAlign = if (isFromUser) TextAlign.Start else TextAlign.End
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(8.dp),
                    maxLines = Int.MAX_VALUE, // Always display the full content
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (!isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.babadogo), // Replace with actual drawable
                contentDescription = "BDogo Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
    }
}


@Preview
@Composable
fun PreviewBabadogoChatScreen() {
    ComposeTutorialTheme {
        BabadogoChatScreen()
    }
}
