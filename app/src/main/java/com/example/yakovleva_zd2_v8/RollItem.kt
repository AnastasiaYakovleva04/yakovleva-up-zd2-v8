package com.example.yakovleva_zd2_v8

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rolls")
data class RollItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: Int,
    val notes: String? = null
)
