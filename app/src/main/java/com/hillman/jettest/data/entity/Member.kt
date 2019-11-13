package com.hillman.jettest.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hillman.jettest.data.Constants
import java.util.*

@Entity(tableName = "Members")
data class Member(
    @SerializedName("_id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "username")
    val username: String?,
    @ColumnInfo(name = "utcOffset")
    val utcOffset: Float?){

    fun avatarUrl(): String{
        return Constants.AVATAR_URL + username + "?format=jpeg"
    }
}

