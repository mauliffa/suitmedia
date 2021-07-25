package com.mauliffa.suitmedia.guest.retrofit

import com.mauliffa.suitmedia.guest.GuestResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("596dec7f0f000023032b8017")
    fun getGuestData(): Call<ArrayList<GuestResponseItem>>
}