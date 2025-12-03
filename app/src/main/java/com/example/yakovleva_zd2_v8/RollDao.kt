package com.example.yakovleva_zd2_v8

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RollDao {
    @Insert
    fun insert(roll: RollItem)

    @Delete
    fun delete(roll: RollItem)

    @Query("SELECT * FROM rolls")
    fun getAll(): List<RollItem>
}