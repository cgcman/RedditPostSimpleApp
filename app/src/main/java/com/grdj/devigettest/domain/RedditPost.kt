package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditPost (
	@SerializedName("kind")
	@Expose
	val kind : String,

	@SerializedName("data")
	@Expose
	val data : Data
) : Parcelable