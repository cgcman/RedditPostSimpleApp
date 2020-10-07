package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
	@SerializedName("modhash") val modhash : String,
	@SerializedName("children") val children : List<Children>,
	@SerializedName("after") val after : String,
	@SerializedName("before") val before : String
) : Parcelable