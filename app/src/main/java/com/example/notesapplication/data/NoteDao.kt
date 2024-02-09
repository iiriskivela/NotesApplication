package com.ahmedapps.roomdatabase.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)

    //@Delete
    //suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note")
    fun getNote(): Flow<Note>

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT title FROM note WHERE id = :noteId") // Adjust this query as per your requirement
    fun getTitleById(noteId: Long): Flow<String>

    /*@Query("SELECT * FROM note ORDER BY dateAdded")
    fun getNotesOrderdByDateAdded(): Flow<List<Note>>


    @Query("SELECT * FROM note ORDER BY name ASC")
    fun getNotesOrderdByTitle(): Flow<List<Note>>*/

}










