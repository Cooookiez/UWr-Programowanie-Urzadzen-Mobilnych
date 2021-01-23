package com.example.studentcrimeapp

import android.net.Uri
import java.util.*

class Crime {
    private var id: UUID
    private var title: String
    private var date: Date
    private var bSolved: Boolean = false
    private var imagePath: String? = null

    constructor() {
        this.id = UUID.randomUUID()
        this.date = Date()
        this.title = "Brand new crime"
        this.bSolved = false
    }

    fun setTitle(title: String) { this.title = title}
    fun getTitle(): String { return this.title }

    fun setDate(date: Date) { this.date = date }
    fun getDate(): Date { return this.date }

    fun setSolvedTo(bSolved: Boolean) { this.bSolved = bSolved }
    fun getSolved(): Boolean { return this.bSolved }

    fun setId(id: UUID) { this.id = id }
    fun getId(): UUID { return this.id }

    fun setImagePath(imagePath: String) { this.imagePath = imagePath }
    fun getImagePath(): String { return this.imagePath.toString() }

}
