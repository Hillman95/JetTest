package com.hillman.jettest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hillman.jettest.data.entity.Member
import com.hillman.jettest.data.repository.MembersRepository
import kotlinx.coroutines.delay

class MembersViewModel(private val state: SavedStateHandle) : ViewModel() {


    companion object{
        private val LIST_POS = "listPos"
        private val LIST_OFFSET = "listOffset"
    }



    private val membersRepository = MembersRepository()
    val members: LiveData<PagedList<Member>> get() = membersRepository.getMembersLiveData()
    var isLoading = true
    private val savedStateHandle = state

    fun saveCurrentPos(position: Int) {
        savedStateHandle.set(LIST_POS, position)
    }

    fun saveCurrentOffset(offset: Int) {
        savedStateHandle.set(LIST_OFFSET, offset)
    }

    fun getCurrentPos(): Int {
        return savedStateHandle.get(LIST_POS)?: 0
    }

    fun getCurrentOffset(): Int {
        return savedStateHandle.get(LIST_OFFSET)?: 0
    }


    fun refreshMembers(){
        membersRepository.refreshMembers()
    }

    fun loadMembers(){
        membersRepository.loadMembers()
    }

    override fun onCleared() {
        super.onCleared()
        membersRepository.completableJob.cancel()
    }
}