package com.guntamania.geminiotameshi.datastore.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val message: String,
    val date: Long, // DateをLong (Unix timestamp) で保存
    val sender: String // Sender enumをStringで保存
)
