package com.hillman.jettest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hillman.jettest.data.entity.Member
import com.hillman.jettest.data.repository.MembersRepository

class MemberDetailsViewModel : ViewModel(){
    private val membersRepository = MembersRepository()

    fun getMemberLivedata(id: String): LiveData<Member>{
        return membersRepository.getMemberById(id)
    }

    override fun onCleared() {
        super.onCleared()
        membersRepository.completableJob.cancel()
    }
}