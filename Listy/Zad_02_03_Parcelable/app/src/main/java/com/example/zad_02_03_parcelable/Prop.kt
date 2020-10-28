package com.example.zad_02_03_parcelable

import android.os.Parcel
import android.os.Parcelable

class Prop(private var a: Int?, private var b: Int?, private var c: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        a = parcel.readValue(Int::class.java.classLoader) as? Int,
        b = parcel.readValue(Int::class.java.classLoader) as? Int,
        c = parcel.readString(),
    )

    fun getA(): Int? { return a }
    fun getB(): Int? { return b }
    fun getC(): String? { return c }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(a)
        parcel.writeValue(b)
        parcel.writeString(c)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Prop> {
        override fun createFromParcel(parcel: Parcel): Prop {
            return Prop(parcel)
        }

        override fun newArray(size: Int): Array<Prop?> {
            return arrayOfNulls(size)
        }
    }

}