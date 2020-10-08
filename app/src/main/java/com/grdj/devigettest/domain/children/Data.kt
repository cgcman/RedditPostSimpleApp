package com.grdj.devigettest.domain.children

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts")
data class Data (
	@NonNull
	@PrimaryKey(autoGenerate = false)
	@SerializedName("id")
	@Expose
	val id : String,

	@SerializedName("thumbnail")
	@Expose
	val thumbnail : String,

	@SerializedName("author")
	@Expose
	val author : String,

	@SerializedName("created")
	@Expose
	val created : Int,

	@SerializedName("title")
	@Expose
	val title : String,

	@SerializedName("num_comments")
	@Expose
	val num_comments : Int,
) : Parcelable