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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacific.ui.theme.ComposeTutorialTheme

class SimtonchatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                SimtonChatScreen()
            }
        }
    }
}

// Data class for Simton chat messages
data class SimtonChatMessage(val author: String, val body: String)

// Simton chat screen composable
@Composable
fun SimtonChatScreen() {
    var messages by remember {
        mutableStateOf(
            mutableListOf(
                SimtonChatMessage(
                    "Simton",
                    """
                    Welcome to Simton Chat! Here are the details you can ask about:
                    
                    • Pricing
                    • Features
                    • Availability
                    • Support
                    
                    Please type your query from the above listed in lowercase to get a response.
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
        SimtonConversation(
            messages = messages,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

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
                        messages.add(SimtonChatMessage("User", userMessage))
                        textState = TextFieldValue("") // Clear input field

                        // Automated response logic
                        val response = getSimtonAutomatedResponse(userMessage)
                        if (response != null) {
                            messages.add(SimtonChatMessage("Simton", response))
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

// Automated response logic for SimtonChat
fun getSimtonAutomatedResponse(input: String): String? {
    return when {
        "features" in input.lowercase() -> "Our system offers real-time analytics, seamless integrations, and customizable workflows."
        "availability" in input.lowercase() -> "Our services are available 24/7 worldwide, with dedicated support in multiple regions."
        "pricing" in input.lowercase() -> "Pricing starts at $29/month for the basic plan and scales based on your needs."
        "support" in input.lowercase() -> "We provide 24/7 support through email, chat, and phone."
        else -> "I'm sorry, I didn't understand that. Could you clarify your question?"
    }
}

// Conversation composable for SimtonChat
@Composable
fun SimtonConversation(messages: List<SimtonChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            SimtonMessageCard(message)
        }
    }
}

// Message card composable for SimtonChat
@Composable
fun SimtonMessageCard(msg: SimtonChatMessage) {
    val surfaceColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary
    )

    val isFromUser = msg.author == "User"

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
                style = MaterialTheme.typography.titleSmall
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
                painter = painterResource(R.drawable.simton), // Replace with actual drawable
                contentDescription = "Simton Profile Picture",
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
fun PreviewSimtonChatScreen() {
    ComposeTutorialTheme {
        SimtonChatScreen()
    }
}
