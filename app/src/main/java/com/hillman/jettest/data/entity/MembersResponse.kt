package com.hillman.jettest.data.entity

data class MembersResponse (
    var members: List<Member>?,
    val count: Int?,
    val offset: Int?,
    val total: Int?,
    val success: Boolean,
    val error: String?,
    val errorType: String?
)