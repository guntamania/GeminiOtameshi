package com.guntamania.geminiotameshi.repository

import com.guntamania.geminiotameshi.datastore.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.guntamania.geminiotameshi.OtameshiApplication
import com.guntamania.geminiotameshi.datastore.entity.MessageEntity

class MessageRepository {
    private val database = AppDatabase.getDatabase(OtameshiApplication.getApplicationContext())

    suspend fun getAllMessages() = withContext(Dispatchers.IO) {
        database.messageDao().getAllMessages()
    }

    suspend fun addMessage(message: MessageEntity) = withContext(Dispatchers.IO) {
        database.messageDao().insertMessage(message)
    }
}