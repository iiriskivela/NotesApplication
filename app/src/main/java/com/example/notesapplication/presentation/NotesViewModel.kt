package com.example.notesapplication.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.roomdatabase.data.Note
import com.ahmedapps.roomdatabase.data.NoteDao
import com.ahmedapps.roomdatabase.presentation.NotesEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
) : ViewModel() {



    private val noteFlow = dao.getNote().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    val _state = MutableStateFlow(NoteState())
    val state =
        combine(_state, noteFlow) { state, note ->
            state.copy(
                title = mutableStateOf(note?.title ?: ""),
                description = mutableStateOf(note?.description ?: "")
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis() // älä poista
                )

                viewModelScope.launch {
                    // Check if a note already exists, if so, update it
                    noteFlow.value?.let { existingNote ->
                        val updatedNote = existingNote.copy(
                            title = note.title,
                            description = note.description
                        )
                        dao.upsertNote(updatedNote)
                    } ?: dao.upsertNote(note)
                }
            }
            // Other events handling if needed
            else -> { }
        }
    }


}