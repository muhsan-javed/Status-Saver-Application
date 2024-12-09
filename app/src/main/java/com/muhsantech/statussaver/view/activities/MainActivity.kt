package com.muhsantech.statussaver.view.activities

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import androidx.fragment.app.Fragment
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.ActivityMainBinding
import com.muhsantech.statussaver.databinding.DialogGuideBinding
import com.muhsantech.statussaver.databinding.ExitCustomDialogBinding
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.utils.SharedPrefKeys
import com.muhsantech.statussaver.utils.SharedPrefUtils
import com.muhsantech.statussaver.utils.replaceFragment
import com.muhsantech.statussaver.utils.slideFromStart
import com.muhsantech.statussaver.utils.slideToEndWithFadeOut
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

//        onBackPressedDispatcher.addCallback(this, onBackBackCallBack)

        binding.apply {
            splashLogic()
            requestPermission()
            val fragmentWhatsAppStatus = FragmentStatus()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
            replaceFragment(fragmentWhatsAppStatus, bundle)
            toolBar.setNavigationOnClickListener {
                drawerLayout.open()
            }

            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_status -> {
                        // whatsapp status
                        val fragmentWhatsAppStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
                        replaceFragment(fragmentWhatsAppStatus, bundle)
                        drawerLayout.close()
                    }

                    R.id.menu_business_status -> {
                        // whatsapp business status
                        val fragmentWhatsAppStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(
                            Constants.FRAGMENT_TYPE_KEY,
                            Constants.TYPE_WHATSAPP_BUSINESS
                        )
                        replaceFragment(fragmentWhatsAppStatus, bundle)
                        drawerLayout.close()
                    }

                    R.id.menu_settings -> {
                        // settings
                        replaceFragment(FragmentSettings())
                        drawerLayout.close()
                    }
                }

                return@setNavigationItemSelectedListener true
            }

        }

        // In your Activity's onCreate()
        onBackPressedDispatcher.addCallback(this, onBackBackCallBack)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Show a default fragment or finish the activity
                replaceFragments(FragmentStatus(), addToBackStack = false)
            }
        }
    }


    private val PERMISSION_REQUEST_CODE = 50
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val isPermissionsGranted = SharedPrefUtils.getPrefBoolean(
                SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                false
            )
            if (!isPermissionsGranted) {
                ActivityCompat.requestPermissions(
                    /* activity = */ activity,
                    /* permissions = */ arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    /* requestCode = */ PERMISSION_REQUEST_CODE
                )
                Toast.makeText(activity, "Please Grant Permissions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (isGranted) {
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED, true)
            } else {
                SharedPrefUtils.putPrefBoolean(
                    SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                    false
                )

            }
        }
    }

    private fun splashLogic() {
        binding.apply {
            splashLayout.cardView.slideFromStart()
            Handler(Looper.myLooper()!!).postDelayed({
                splashScreenHolder.slideToEndWithFadeOut()
                splashScreenHolder.visibility = View.GONE
            }, 3000)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.fragment_container)
        fragment?.onActivityResult(requestCode, resultCode, data)

    }

    // Exit App Dialog Function
    private fun exitDialog() {
        val dialog = Dialog(this)
        val dialogBinding = ExitCustomDialogBinding.inflate((activity).layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true) // NOT CLOSE
        dialogBinding.apply {
            no.setOnClickListener {
                dialog.dismiss()
            }
            yes.setOnClickListener {
                Toast.makeText(activity, resources.getString(R.string.app_name), Toast.LENGTH_SHORT).show()
                //showFullAd()
                finishAffinity()
            }
        }
        dialog.show()
    }

    fun replaceFragments(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    // BackPressed Manage
    private val onBackBackCallBack = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (supportFragmentManager.backStackEntryCount == 1) {
                // Show exit dialog or finish the act ivity
                exitDialog()
            } else if (supportFragmentManager.backStackEntryCount > 1 ) {
                // Navigate back in the fragment back stack
                supportFragmentManager.popBackStack()
            } else {
                // Back stack is empty, handle accordingly (e.g., show a default fragment)
                // Example: replaceFragment(HomeFragment(), addToBackStack = false)
                finishAffinity()
            }
        }

    }
}