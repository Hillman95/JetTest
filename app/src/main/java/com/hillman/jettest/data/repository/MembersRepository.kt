package com.hillman.jettest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.hillman.jettest.App
import com.hillman.jettest.data.api.ApiService
import com.hillman.jettest.data.db.MemberDatabase
import com.hillman.jettest.data.entity.Member
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.concurrent.Executors

class MembersRepository {

    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private var count = 50
    private var offset = 0
    private var total = 100
    private val dao = MemberDatabase.getInstance(App.context!!).memberDao()

    var isLoading = false
    val isLastPage
        get() = count*offset >= total


    private val thisApiCorService by lazy {
        ApiService.createCorService() }


  fun refreshMembers(){
      offset = 0
      coroutineScope.launch {
          try {
              dao.deleteAllMembers()
          }catch (e: Throwable){}
          finally {
              loadMembers()
          }
        }

    }

    fun loadMembers() {
        if (!isLastPage && !isLoading){
        coroutineScope.launch {
            delay(5000)
            isLoading = true
            val request = thisApiCorService.getMembersAsync(headers(), offset, count)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.success && response.members != null) {
                        dao.insertAllMembers(response.members!!)
                        total = response.total ?: 0
                        offset++
                    }

                } catch (e: HttpException) { }
                  catch (e: Throwable) { }
                  finally { isLoading = false }
            }
        }
      }
    }


    fun getMembersLiveData(): LiveData<PagedList<Member>>{
        val pagedListLiveData  by lazy {
            val dataSourceFactory = dao.getAllPaged()
            val config = PagedList.Config.Builder()
                .setPageSize(count)
                .setInitialLoadSizeHint(count)
                .build()
            LivePagedListBuilder(dataSourceFactory, config).build()
        }
        return pagedListLiveData
    }

    fun getMemberById(id: String): LiveData<Member>{
        return dao.getMember(id)
    }



    private fun headers() = mapOf(
        "X-Auth-Token" to "kc8AHBZlH3iCObf9alUrfpDkUoBAEsYoEEw3JBemQq3",
        "X-User-Id" to "e2qQQA3adTsZGN8et")
}