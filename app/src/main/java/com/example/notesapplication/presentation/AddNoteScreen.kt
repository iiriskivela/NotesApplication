package com.example.notesapplication.presentation


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ahmedapps.roomdatabase.presentation.NotesEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {

    val context = LocalContext.current

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var selectedImageFile by remember {
        mutableStateOf<File?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                //selectedImageUri = uri

                // Copy the selected image URI to internal storage
                val copiedFile = copyImageToAppStorage(context, context.contentResolver, uri)
                if (copiedFile != null) {
                    Log.d("PhotoPicker", "Image copied to internal storage: ${copiedFile.absolutePath}")
                    // Update the state with the copied image URI
                    state.description.value = copiedFile.absolutePath
                    selectedImageFile = copiedFile
                } else {
                    Log.e("PhotoPicker", "Failed to copy image to internal storage")
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                onEvent(NotesEvent.SaveNote(
                    title = state.title.value,
                    description = state.description.value
                ))
                navController.popBackStack()
            }) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Save and go back"
                )

            }
        }
    ) { paddingValues ->

    Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title.value,
                onValueChange = {
                    state.title.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "Enter name:")
                }
            )

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                }
            ) {
                state.description.value?.let { filePath ->
                    val profilePictureFile = File(filePath)
                    Image(
                        painter = rememberImagePainter(profilePictureFile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                /*selectedImageFile?.let { file ->
                    Image(
                        painter = rememberImagePainter(file),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }*/
                /*selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(uri.toString()), //(uri)
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }*/
            }

        }

    }

}

fun copyImageToAppStorage(context: Context, contentResolver: ContentResolver, imageUri: Uri): File? {
    val inputStream = contentResolver.openInputStream(imageUri)
    val filesDir = context.filesDir
    val outputFile = File(filesDir, "copied_image.jpg")

    try {
        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return outputFile
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}