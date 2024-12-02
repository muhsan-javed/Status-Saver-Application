package com.muhsantech.statussaver.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.anggrayudi.storage.FileWrapper
import com.muhsantech.statussaver.models.MediaModel
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.utils.SharedPrefKeys
import com.muhsantech.statussaver.utils.SharedPrefUtils

class StatusRepo(val context: Context) {
    val whatsAppStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsAppBusinessStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()

    val activity  = context as Activity

    private val wpStatusesList = ArrayList<MediaModel>()
    private val wpBusinessStatusesList = ArrayList<MediaModel>()

    fun getAllStatusesUser(whatsAppType: String = Constants.TYPE_WHATSAPP_MAIN){

        val treeUri = when(whatsAppType){
            Constants.TYPE_WHATSAPP_MAIN->{
                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!
            }
            else->{
                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, "")?.toUri()!!

            }
        }

        activity.contentResolver.takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val fileDocument = DocumentFile.fromTreeUri(activity, treeUri)

        fileDocument?.let {
            it.listFiles().forEach { file->

                if (file.name != ".nomedia" && file.isFile){

                }

            }
        }

    }


}