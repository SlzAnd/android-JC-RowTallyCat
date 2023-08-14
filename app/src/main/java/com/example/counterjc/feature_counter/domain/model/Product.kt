package com.example.counterjc.feature_counter.domain.model

import android.os.Message
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


class InvalidProductException(message: String) : Exception(message)
