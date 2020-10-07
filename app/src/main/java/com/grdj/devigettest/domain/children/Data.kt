package com.grdj.devigettest.domain.children

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
	@SerializedName("domain") val domain : String,
	@SerializedName("banned_by") val banned_by : String,
	@SerializedName("subreddit") val subreddit : String,
	@SerializedName("selftext_html") val selftext_html : String,
	@SerializedName("selftext") val selftext : String,
	@SerializedName("likes") val likes : String,
	@SerializedName("user_reports") val user_reports : List<String>,
	@SerializedName("secure_media") val secure_media : String,
	@SerializedName("link_flair_text") val link_flair_text : String,
	@SerializedName("id") val id : String,
	@SerializedName("gilded") val gilded : Int,
	@SerializedName("clicked") val clicked : Boolean,
	@SerializedName("report_reasons") val report_reasons : String,
	@SerializedName("author") val author : String,
	@SerializedName("media") val media : String,
	@SerializedName("score") val score : Int,
	@SerializedName("approved_by") val approved_by : String,
	@SerializedName("over_18") val over_18 : Boolean,
	@SerializedName("hidden") val hidden : Boolean,
	@SerializedName("thumbnail") val thumbnail : String,
	@SerializedName("subreddit_id") val subreddit_id : String,
	@SerializedName("edited") val edited : Boolean,
	@SerializedName("link_flair_css_class") val link_flair_css_class : String,
	@SerializedName("author_flair_css_class") val author_flair_css_class : String,
	@SerializedName("downs") val downs : Int,
	@SerializedName("mod_reports") val mod_reports : List<String>,
	@SerializedName("saved") val saved : Boolean,
	@SerializedName("is_self") val is_self : Boolean,
	@SerializedName("name") val name : String,
	@SerializedName("permalink") val permalink : String,
	@SerializedName("stickied") val stickied : Boolean,
	@SerializedName("created") val created : Int,
	@SerializedName("url") val url : String,
	@SerializedName("author_flair_text") val author_flair_text : String,
	@SerializedName("title") val title : String,
	@SerializedName("created_utc") val created_utc : Int,
	@SerializedName("ups") val ups : Int,
	@SerializedName("num_comments") val num_comments : Int,
	@SerializedName("visited") val visited : Boolean,
	@SerializedName("num_reports") val num_reports : String,
	@SerializedName("distinguished") val distinguished : String
) : Parcelable