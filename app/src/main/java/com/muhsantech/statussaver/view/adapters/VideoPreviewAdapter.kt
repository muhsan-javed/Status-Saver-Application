package com.muhsantech.statussaver.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.ItemImagePreviewBinding
import com.muhsantech.statussaver.databinding.ItemVideoPreviewBinding
import com.muhsantech.statussaver.models.MEDIA_TYPE_IMAGE
import com.muhsantech.statussaver.models.MediaModel
import com.muhsantech.statussaver.utils.saveStatus

class VideoPreviewAdapter(val list:ArrayList<MediaModel>, val context: Context
) : RecyclerView.Adapter<VideoPreviewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVideoPreviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(mediaModel: MediaModel){

                binding.apply {

                    //Video Player Implementation
                    val player = ExoPlayer.Builder(context).build()
                    playerView.player = player
                    val mediaItem = MediaItem.fromUri(mediaModel.pathUri)
                    player.setMediaItem(mediaItem)
                    player.prepare()
//                    player.play()


                    // Download Implementation
                    val downloadImage = if (mediaModel.isDownloaded){
                        R.drawable.ic_downloaded
                    }else {
                        R.drawable.ic_download
                    }

                    tools.statusDownload.setImageResource(downloadImage)


                    tools.download.setOnClickListener {
                        val isDownloaded = context.saveStatus(mediaModel)
                        if (isDownloaded){
                            // status is download
                            Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                            mediaModel.isDownloaded = true
                            tools.statusDownload.setImageResource(R.drawable.ic_downloaded)
                        }else{
                            // status is download
                            Toast.makeText(context, "Unable to Save", Toast.LENGTH_SHORT).show()

                        }
                    }

                }

        }
    fun stopPlayer(){
        binding.playerView.player?.stop()
    }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVideoPreviewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount() = list.size
}