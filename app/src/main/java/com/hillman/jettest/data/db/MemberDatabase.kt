package com.hillman.jettest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hillman.jettest.data.entity.Member

@Database(entities = [(Member::class)], version = 1, exportSchema = false)
abstract class MemberDatabase : RoomDatabase(){

    abstract fun memberDao() : MemberDao

    companion object {
        private const val DATABASE_NAME = "members"
        @Volatile private var instance: MemberDatabase? = null

        fun getInstance(context: Context): MemberDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MemberDatabase {
            return Room.databaseBuilder(context, MemberDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}