package com.example.counterjc.feature_counter.domain.use_case

import android.content.Context
import com.example.counterjc.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadBackgroundImage(
    val context: Context
) {

    suspend operator fun invoke(): String {
        return try {
            withContext(Dispatchers.IO) {
                val files = context.filesDir.listFiles()
                files?.filter {
                    it.canRead() && it.isFile && it.name.endsWith( ".jpg")
                }?.get(0)?.path ?: "android.resource://com.example.counterjc/${R.drawable.mountain}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "android.resource://com.example.counterjc/${R.drawable.mountain}"
        }
    }
}