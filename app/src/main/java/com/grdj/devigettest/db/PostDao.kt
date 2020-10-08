package com.grdj.devigettest.db

import androidx.room.*
import com.grdj.devigettest.domain.children.Data

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getPostList(): List<Data>

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    suspend fun getPost(id: String): Data

    @Delete
    suspend fun deletePost(post: Data)
}