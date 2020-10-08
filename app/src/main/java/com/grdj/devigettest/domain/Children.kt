package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import com.grdj.devigettest.domain.children.Data

@Parcelize
data class Children (
	@SerializedName("kind")
	@Expose
	val kind : String,

	@SerializedName("data")
	@Expose
	val data : Data
): Parcelable