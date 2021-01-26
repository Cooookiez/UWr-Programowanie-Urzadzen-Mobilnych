package com.example.zad_10_05_mvvm_localroom.utils

import android.app.Application
import com.example.zad_10_05_mvvm_localroom.database.WordRoom
import com.example.zad_10_05_mvvm_localroom.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {WordRoom.getDatabase(this, applicationScope)}
    val repository by lazy {WordRepository(database.wordDAO())}
}