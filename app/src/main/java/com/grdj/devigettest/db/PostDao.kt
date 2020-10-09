package com.grdj.devigettest.db

import androidx.room.*
import com.grdj.devigettest.domain.children.Data

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getPostList(): List<Data>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPosts(posts: List<Data>)

    @Delete
    suspend fun deletePost(post: Data)
}