package com.example.notesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.ahmedapps.roomdatabase.data.Note
import com.ahmedapps.roomdatabase.presentation.NotesEvent
import com.example.notesapplication.presentation.AddNoteScreen
import com.example.notesapplication.presentation.NoteState
import com.example.notesapplication.presentation.NotesViewModel
import java.io.File

data class Message(val author: String, val body: String, val note: Note?)

@Composable
fun MessageCard(
    msg: Message,
    state: NoteState
) {

    Row(modifier = Modifier.padding(all = 8.dp)) {
        //profile picture
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .background(Color.LightGray)
        ) {
            // Display profile picture
            state.description.value?.let { filePath ->
                val profilePictureFile = File(filePath)
                Image(
                    painter = rememberImagePainter(profilePictureFile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        // We keep track if the message is expanded or not in this variable
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {

            Text(
                text = state.title.value,//msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/*
//@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    Surface {
        MessageCard(
            msg = Message("Iiris", "Hi there!")
        )
    }
}*/

@Composable
fun Conversation(
    navController: NavController,
    messages: List<Message>,
    state: NoteState,
    onEvent: (NotesEvent) -> Unit

) {

    LazyColumn {
        item {
            // Row to hold both buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Align items at the start and end
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Settings button at the top left


                // Home button at the top right
                /*FloatingActionButton(
                    onClick = {
                        navController.navigate("NotesScreen")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }*/
                FloatingActionButton(
                    onClick = {
                        navController.navigate("AddNoteScreen")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                }
            }
        }
        items(messages) { message ->
            MessageCard(message, state)
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun PreviewConversation() {
    val navController = rememberNavController()
    Conversation(navController, SampleData.conversationSample)
}*/
