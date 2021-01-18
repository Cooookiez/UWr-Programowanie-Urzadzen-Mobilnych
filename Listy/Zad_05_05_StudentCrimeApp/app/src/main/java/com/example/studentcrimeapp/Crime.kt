package com.example.studentcrimeapp

import java.util.*

class Crime {
    private lateinit var id: UUID
    private lateinit var title: String
    private lateinit var date: Date
    private var bSolved: Boolean = false

    constructor() {
        this.id = UUID.randomUUID()
        this.date = Date()
    }
    constructor(id: UUID) {
        this.id = id
    }

    fun setTitle(title: String) { this.title = title}
    fun getTitle(): String { return this.title }

    fun setDate(date: Date) { this.date = date }
    fun getDate(): Date { return this.date }

    fun setSolvedTo(bSolved: Boolean) { this.bSolved = bSolved }
    fun getSolved(): Boolean { return this.bSolved }

    fun setId(id: UUID) { this.id = id }
    fun getId(): UUID { return this.id }

}
