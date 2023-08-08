package com.example.counterjc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    val name: String,

    @ColumnInfo(name="modified_at")
    var modifiedAt: Long,

    val currentCounterValue: Int = 0,

    val goalValue: Int = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
