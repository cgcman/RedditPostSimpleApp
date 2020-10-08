package com.grdj.devigettest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class ChildrenDataEntity(
    @PrimaryKey(autoGenerate = true) val id: String = "0",
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "created") val created: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "num_comments") val num_comments: Int
)