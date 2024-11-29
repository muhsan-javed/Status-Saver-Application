package com.muhsantech.statussaver.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.ActivityMainBinding
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.utils.SharedPrefUtils
import com.muhsantech.statussaver.utils.replaceFragment
import com.muhsantech.statussaver.view.fragments.FragmentSettings
import com.muhsantech.statussaver.view.fragments.FragmentStatus

class MainActivity : AppCompatActivity() {
    private val activity = this

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
        SharedPrefUtils.init(activity)

        //Default whatsapp status
        val fragmentWhatsAppStatus = FragmentStatus()
        val bundle = Bundle()
        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
        replaceFragment(fragmentWhatsAppStatus, bundle)

        binding.apply {

            bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_status->{
                        //whatspp status
                        val fragmentWhatsAppStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
                        replaceFragment(fragmentWhatsAppStatus, bundle)
                    }
                    R.id.menu_business_status->{
                        //whatspp Buusines status
                        val fragmentWhatsAppStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_BUSINESS)
                        replaceFragment(fragmentWhatsAppStatus, bundle)
                    }
                    R.id.menu_settings->{
                        replaceFragment(FragmentSettings())
                    }
                }
                return@setOnItemSelectedListener true
            }

        }

      /*  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.fragment_container)
        fragment?.onActivityResult(requestCode, resultCode, data)

    }
}