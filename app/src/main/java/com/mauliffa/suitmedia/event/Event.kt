package com.mauliffa.suitmedia.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    var eventName: String,
    var eventDate: String,
    var eventPhoto: Int,
    var eventTheme: String,
    var eventDesc: String,
    var eventLongitude: String,
    var eventLatitude: String
): Parcelable
