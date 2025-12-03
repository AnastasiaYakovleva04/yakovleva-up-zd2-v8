package com.example.yakovleva_zd2_v8

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RollItem::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun rollDao(): RollDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rolls_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}