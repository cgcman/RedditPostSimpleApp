package com.grdj.devigettest.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.grdj.devigettest.domain.children.Data
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Children (
	@SerializedName("kind") val kind : String,
	@SerializedName("data") val data : Data
): Parcelable