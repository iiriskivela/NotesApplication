package com.ahmedapps.roomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    val title: String,
    val description: String, // uri of profile picture
    val dateAdded: Long, // älä poista

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)