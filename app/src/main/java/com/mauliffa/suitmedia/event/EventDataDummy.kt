package com.mauliffa.suitmedia.event

import com.mauliffa.suitmedia.R

object EventDataDummy {
    private val name = arrayOf(
        "Online Counseling",
        "Beauty Class 2021",
        "Webinar: Financial Planner",
        "Webinar: Self Acceptance",
        "SuitChill 2021",
        "Webinar: Saya Salah Jurusan?",
        "Suitmedia Goes to Campus",
        "SuitSports2020 Yoga Lates"
    )

    private val date = arrayOf(
        "24 Juli 2021",
        "2 Juni 2021",
        "19 Mei 2021",
        "12 Mei 2021",
        "25 Maret 2021",
        "2 Januari 2021",
        "17 Desember 2020",
        "9 November 2020"
    )

    private val photo = intArrayOf(
        R.drawable.event_counseling,
        R.drawable.event_beauty,
        R.drawable.event_webinar,
        R.drawable.event_webinar,
        R.drawable.event_movie,
        R.drawable.event_webinar,
        R.drawable.event_campus,
        R.drawable.event_yoga
    )

    val listData: ArrayList<Event>
        get() {
            val list = arrayListOf<Event>()
            for (position in name.indices) {
                val event = Event(
                    eventName = name[position],
                    eventDate = date[position],
                    eventPhoto = photo[position]
                )
                list.add(event)
            }
            return list
        }
}