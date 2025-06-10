package com.guntamania.geminiotameshi.datastore.dao

import androidx.room.Dao
import androidx.room.Query
import com.guntamania.geminiotameshi.datastore.entity.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY date ASC")
    fun getAllMessages(): List<MessageEntity>

    @androidx.room.Insert
    fun insertMessage(message: MessageEntity)

    @androidx.room.Delete
    fun deleteMessage(message: MessageEntity)
}
