package com.example.spacific

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacific.ui.theme.ComposeTutorialTheme

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

// Data class for Babadogo chat messages
data class BabadogoChatMessage(val author: String, val body: String)

// Sample data for Babadogo chat preview
object BabadogoSampleData {
    val conversationSample = listOf(
        BabadogoChatMessage("Chris", "Did you see the new Jetpack Compose update?"),
        BabadogoChatMessage("Taylor", "Yes! It’s packed with so many new features."),
        BabadogoChatMessage("Chris", "I'm especially excited about the animation improvements."),
        BabadogoChatMessage("Taylor", "Definitely a game-changer for animations in Compose."),
        BabadogoChatMessage("Chris", "Can't wait to implement them in our project!"),
    )
}

@Composable
fun BabadogoChatScreen() {
    var messages by remember { mutableStateOf(BabadogoSampleData.conversationSample.toMutableList()) }
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        BabadogoConversation(
            messages = messages,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .imePadding()
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                placeholder = { Text("Enter your message") },
            )

            Button(
                onClick = {
                    if (textState.text.isNotEmpty()) {
                        messages.add(BabadogoChatMessage("Chris", textState.text))
                        textState = TextFieldValue("")
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun BabadogoConversation(messages: List<BabadogoChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            BabadogoMessageCard(message)
        }
    }
}

@Composable
fun BabadogoMessageCard(msg: BabadogoChatMessage) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    val surfaceColor by animateColorAsState(
        if (isHovered || isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    )

    val isFromUser = msg.author == "Chris"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isFromUser) Arrangement.Start else Arrangement.End
    ) {
        if (!isFromUser) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Contact Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { isExpanded = !isExpanded },
                        onPress = {
                            isHovered = true
                            tryAwaitRelease()
                            isHovered = false
                        }
                    )
                }
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
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.app),
                contentDescription = "Contact Profile Picture",
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
