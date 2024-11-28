package com.muhsantech.statussaver.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhsantech.statussaver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activity = this

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)

        binding.apply {

        }

      /*  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

    }


}