package com.grdj.devigettest.repository

import com.grdj.devigettest.api.ErrorManagerHelper
import com.grdj.devigettest.api.RedditApiService
import com.grdj.devigettest.api.ResultWrapper
import com.grdj.devigettest.db.PostDao
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.domain.children.Data
import com.grdj.devigettest.util.network.NetworkManager

class RepositoryImp(
    private val service: RedditApiService,
    private val networkManager: NetworkManager,
    private val postDao: PostDao,
    private val errorManagerHelper: ErrorManagerHelper
) : Repository  {

    override suspend fun getRedditPost(): ResultWrapper<List<Children>?> {
        if(networkManager.isConnected()){
            val response = service.getRedditPost()
            when (response) {
                is ResultWrapper.NetworkError -> ResultWrapper.NetworkError
                is ResultWrapper.GenericError -> ResultWrapper.GenericError()
                is ResultWrapper.Success -> {
                    val children : List<Children>? = response.value
                    val roomDataList = ArrayList<Data>(arrayListOf())
                    for (child in children!!) {
                        roomDataList.add(child.data)
                    }
                    insertAll(roomDataList)
                    ResultWrapper.Success(children)
                }
            }
            return response
        } else {
            val response = getPostListFromDb()
            when (response) {
                is ResultWrapper.GenericError -> ResultWrapper.GenericError()
                is ResultWrapper.Success -> {
                    ResultWrapper.Success(response)
                }
            }
            return response
        }
    }

    suspend fun getPostListFromDb() : ResultWrapper<List<Children>> {
        return errorManagerHelper.dataCall() {
            val response = mapDB()
            when (response.size) {
                0 -> throw Exception("DB is Empty")
                else -> response
            }
        }
    }

    suspend fun insertAll(post: ArrayList<Data>) : ResultWrapper<Unit> {
        return errorManagerHelper.dataCall() {
            postDao.insertPosts(post)
        }
    }

    override suspend fun deleteReddit(data: Data) : ResultWrapper<Unit> {
        return errorManagerHelper.dataCall() {
            try {
                postDao.deletePost(data)
            } catch (error: Exception){
                throw Exception("DB ierror: "+error)
            }
        }
    }

    suspend fun mapDB() : List<Children>{
        val childrens = ArrayList<Children>(arrayListOf())
        for (child in postDao.getPostList()) {
            val children = Children("", child)
            childrens.add(children)
        }
        return childrens
    }
}