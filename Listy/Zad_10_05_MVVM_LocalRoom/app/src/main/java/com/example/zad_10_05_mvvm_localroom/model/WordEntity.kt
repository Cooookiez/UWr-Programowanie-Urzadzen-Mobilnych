package com.example.zad_10_05_mvvm_localroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class WordEntity (
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
        )