package com.danrsy.rgithubuser.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danrsy.rgithubuser.core.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], exportSchema = false, version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UsersDao

}