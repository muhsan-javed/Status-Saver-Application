package com.muhsantech.statussaver.utils

import android.content.Context
import android.os.Environment
import com.muhsantech.statussaver.R
import java.io.File

fun Context.isStatusExist(fileName: String): Boolean {
    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File("${downloadDir}/${getString(R.string.app_name)}", fileName)
    return file.exists()
}


fun getFileExtension(fileName: String): String {
    val lastDotIndex = fileName.lastIndexOf(".")

    if (lastDotIndex >= 0 && lastDotIndex < fileName.length - 1) {
        return fileName.substring(lastDotIndex + 1)
    }
    return ""
}