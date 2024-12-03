package com.muhsantech.statussaver.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.ActivityVideosPreviewBinding
import com.muhsantech.statussaver.models.MediaModel
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.view.adapters.ImagePreviewAdapter
import com.muhsantech.statussaver.view.adapters.VideoPreviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideosPreview : AppCompatActivity() {

    private val binding by lazy {
        ActivityVideosPreviewBinding.inflate(layoutInflater)
    }
    lateinit var adapter: VideoPreviewAdapter
    private val TAG = "VideosPreview"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)

            adapter = VideoPreviewAdapter(list, this@VideosPreview)
            videoRecyclerview.adapter = adapter
            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(videoRecyclerview)
            videoRecyclerview.scrollToPosition(scrollTo)

            videoRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        Log.d(TAG, "onScrollStateChanged: Dragging")
                        stopAllPlayer()
                    }
                }
            })
        }
        /*enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/


    }

    private fun stopAllPlayer(){
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                binding.apply {
                    for (i in 0 until videoRecyclerview.childCount){
                        val child = videoRecyclerview.getChildAt(i)
                        val viewHolder = videoRecyclerview.getChildViewHolder(child)
                        if (viewHolder is VideoPreviewAdapter.ViewHolder){
                            viewHolder.stopPlayer()
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopAllPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAllPlayer()
    }
}