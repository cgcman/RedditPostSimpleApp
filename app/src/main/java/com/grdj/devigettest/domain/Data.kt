package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
	@SerializedName("modhash")
	@Expose
	val modhash : String,

	@SerializedName("children")
	@Expose
	val children : List<Children>,

	@SerializedName("after")
	@Expose
	val after : String,

	@SerializedName("before")
	@Expose
	val before : String
) : Parcelable