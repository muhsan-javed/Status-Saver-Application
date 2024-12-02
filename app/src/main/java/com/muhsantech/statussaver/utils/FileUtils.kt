package com.muhsantech.statussaver.utils

import android.content.Context
import android.os.Environment
import com.muhsantech.statussaver.R
import java.io.File

fun Context.isStatusExist(fileName:String): Boolean {
    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File("${downloadDir}/${getString(R.string.app_name)}",)
    return file.exists()
}