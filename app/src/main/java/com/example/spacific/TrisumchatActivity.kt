package com.example.spacific

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacific.ui.theme.ComposeTutorialTheme

class TrisumchatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                TrisumChatScreen()
            }
        }
    }
}

// Data class for messages
data class TrisumChatMessage(val author: String, val body: String)

// Trisum chat screen composable
@Composable
fun TrisumChatScreen() {
    var messages by remember {
        mutableStateOf(
            mutableListOf(
                TrisumChatMessage(
                    "Trisum",
                    """
                    Welcome to Trisum Chat! Here are the details you can ask about:
                    
                    • Services
                    • Pricing
                    • Support
                    
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
        TrisumConversation(
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
                        messages.add(TrisumChatMessage("Mark", userMessage))
                        textState = TextFieldValue("")

                        // Automated response logic
                        val response = getTrisumAutomatedResponse(userMessage)
                        if (response != null) {
                            messages.add(TrisumChatMessage("Trisum", response))
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

// Automated response for TrisumChat
fun getTrisumAutomatedResponse(input: String): String? {
    return when {
        "services" in input.lowercase() -> "Our services include tech consultations, development, and support."
        "pricing" in input.lowercase() -> "Pricing depends on the project, but hourly rates start at $50."
        "support" in input.lowercase() -> "We offer 24/7 customer support to ensure your success."
        else -> "I'm sorry, I didn't catch that. Could you clarify your question?"
    }
}

// Conversation composable
@Composable
fun TrisumConversation(messages: List<TrisumChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            TrisumMessageCard(message)
        }
    }
}

// Message card composable
@Composable
fun TrisumMessageCard(msg: TrisumChatMessage) {
    val surfaceColor by animateColorAsState(
        if (msg.author == "Trisum") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
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
                painter = painterResource(R.drawable.profile_picture),
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
                modifier = Modifier.padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (!isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.trisum),
                contentDescription = "Trisum Profile Picture",
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
fun PreviewTrisumChatScreen() {
    ComposeTutorialTheme {
        TrisumChatScreen()
    }
}
