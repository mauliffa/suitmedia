package com.mauliffa.suitmedia.guest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guest(
    var id: Int,
    var guestName: String,
    var guestBirthdate: String,
    var guestPhoto: Int
): Parcelable
