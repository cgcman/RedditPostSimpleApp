package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditPost (
	@SerializedName("kind") val kind : String,
	@SerializedName("data") val data : Data
) : Parcelable