package com.hillman.jettest.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.hillman.jettest.data.entity.Member

@Dao
interface MemberDao {

    @Query("SELECT * FROM Members")
    fun getAllPaged(): DataSource.Factory<Int, Member>

    @Query("SELECT * FROM Members WHERE id=:id")
    fun getMember(id: String): LiveData<Member>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMembers(members: List<Member>)

    @Query("DELETE FROM Members")
    suspend fun deleteAllMembers()
}
