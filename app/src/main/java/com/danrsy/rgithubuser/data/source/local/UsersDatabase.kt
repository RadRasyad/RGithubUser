package com.danrsy.rgithubuser.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danrsy.rgithubuser.data.model.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): UsersDatabase {
            if (INSTANCE == null) {
                synchronized(UsersDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "Users.db"
                    ).build()
                }
            }
            return INSTANCE as UsersDatabase
        }
    }
}