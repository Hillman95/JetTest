package com.hillman.jettest

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.hillman.jettest.data.db.MemberDatabase


class App : Application(){

    companion object {
        var context: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        context = this

    }
}