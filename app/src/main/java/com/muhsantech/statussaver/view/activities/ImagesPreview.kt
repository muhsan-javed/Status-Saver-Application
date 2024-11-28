package com.muhsantech.statussaver.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhsantech.statussaver.databinding.ActivityVideosPreviewBinding

class ImagesPreview : AppCompatActivity() {
    private val binding by lazy {
        ActivityVideosPreviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        /* enableEdgeToEdge()
         setContentView(R.layout.activity_images_preview)
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }*/
    }

}