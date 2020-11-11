package com.example.zad_02_06_physicsquizextended

import android.os.Parcel
import android.os.Parcelable

data class Question(private var text: String?, private var answer: Boolean?, private var answered: Boolean? = false, private var cheated: Boolean? = false) : Parcelable {
    constructor(parcel: Parcel) : this(
        text = parcel.readString(),
        answer = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        answered = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        cheated = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    fun getText(): String? { return text }
    fun getAnswer(): Boolean? { return answer }
    fun getAnswered(): Boolean? { return answered }
    fun getCheated(): Boolean? { return cheated }

    fun setAnswered(answered: Boolean) { this.answered = answered }
    fun setCheated(cheated: Boolean) { this.cheated = cheated }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeValue(answer)
        parcel.writeValue(answered)
        parcel.writeValue(cheated)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}