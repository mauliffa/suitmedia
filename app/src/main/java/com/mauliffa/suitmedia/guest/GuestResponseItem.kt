package com.mauliffa.suitmedia.guest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GuestResponseItem(
	@field:SerializedName("birthdate")
	val birthdate: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
): Parcelable
