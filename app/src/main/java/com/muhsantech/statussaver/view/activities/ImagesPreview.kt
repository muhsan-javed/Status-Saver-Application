package com.muhsantech.statussaver.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhsantech.statussaver.databinding.ActivityImagesPreviewBinding
import com.muhsantech.statussaver.databinding.ActivityVideosPreviewBinding
import com.muhsantech.statussaver.models.MediaModel
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.view.adapters.ImagePreviewAdapter

class ImagesPreview : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityImagesPreviewBinding.inflate(layoutInflater)
    }
    lateinit var adapter: ImagePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val list =
                intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)
            adapter = ImagePreviewAdapter(list, activity)
            imagesViewPager.adapter = adapter
            imagesViewPager.currentItem = scrollTo
        }

        /* enableEdgeToEdge()
         setContentView(R.layout.activity_images_preview)
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }*/
    }

}