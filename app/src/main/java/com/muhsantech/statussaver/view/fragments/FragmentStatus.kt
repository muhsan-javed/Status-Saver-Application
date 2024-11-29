package com.muhsantech.statussaver.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muhsantech.statussaver.databinding.FragmentStatusBinding
import com.muhsantech.statussaver.utils.Constants
import com.muhsantech.statussaver.utils.SharedPrefKeys
import com.muhsantech.statussaver.utils.SharedPrefUtils
import com.muhsantech.statussaver.utils.getFolderPermissions
import com.muhsantech.statussaver.view.adapters.MediaViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentStatus : Fragment() {

    private val binding by lazy {
        FragmentStatusBinding.inflate(layoutInflater)
    }

    private lateinit var type:String
    private val WHATSAPP_REQUEST_CODE = 101
    private val WHATSAPP_BUSINESS_REQUEST_CODE = 102

    private val viewPagerTitles = arrayListOf("images","videos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding.apply {
            arguments?.let {
                type = it.getString(Constants.FRAGMENT_TYPE_KEY,"")
                tempText.text = type
                when(type){
                    Constants.TYPE_WHATSAPP_MAIN->{
                        //WhatsApp Status
                        // Check permission
                        // granted then fetch statuses
                        // get Permission
                        //fetch statuses
                        val  isPermissionGranted = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)
                        if (isPermissionGranted){
                            getWhatsappStatuses()
                        }
                        permissionLayout.btnPermissions.setOnClickListener {
                            getFolderPermissions(
                                context = requireContext(),
                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                initialUri = Constants.getWhatsappUri()
                            )
                        }

                        val viewPagerAdapter = MediaViewPagerAdapter(requireActivity())
                        statusViewPager.adapter = viewPagerAdapter
                        TabLayoutMediator(tabLayout, statusViewPager) { tab, pos->
                            tab.text = viewPagerTitles[pos]
                        }.attach()

                    }
                    Constants.TYPE_WHATSAPP_BUSINESS-> {
                        val  isPermissionGranted = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, false)
                        if (isPermissionGranted){
                            getWhatsappStatuses()
                        }
                        //WhatsApp Business Status
                        permissionLayout.btnPermissions.setOnClickListener {
                            getFolderPermissions(
                                context = requireContext(),
                                REQUEST_CODE = WHATSAPP_BUSINESS_REQUEST_CODE,
                                initialUri = Constants.getWhatsappBusinessUri()
                            )
                        }


                        val viewPagerAdapter = MediaViewPagerAdapter(requireActivity(),
                            Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES,Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS)
                        statusViewPager.adapter = viewPagerAdapter
                        TabLayoutMediator(tabLayout, statusViewPager) { tab, pos->
                            tab.text = viewPagerTitles[pos]
                        }.attach()
                    }
                }

           }

       }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    // funcation to get whatsapp statuses
    fun getWhatsappStatuses(){
        binding.permissionLayoutHolder.visibility = View.GONE
    }

    fun getWhatsappBusinessStatuses(){
        binding.permissionLayoutHolder.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == AppCompatActivity.RESULT_OK){
            val treeUri = data?.data!!
            requireActivity().contentResolver.takePersistableUriPermission(
                treeUri,Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            if (requestCode == WHATSAPP_REQUEST_CODE){
                // whatsapp logic here
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_TREE_URI,
                    treeUri.toString()
                )

                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, true)
                getWhatsappStatuses()
            }
            else if (requestCode == WHATSAPP_BUSINESS_REQUEST_CODE){
                //Business whatsapp logic here
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI,
                    treeUri.toString()
                )

                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, true)
                getWhatsappBusinessStatuses()
            }


        }


    }






}