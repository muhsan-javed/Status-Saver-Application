package com.muhsantech.statussaver.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.FragmentSettingsBinding
import com.muhsantech.statussaver.models.SettingsModel
import com.muhsantech.statussaver.view.adapters.SettingsAdapter

class FragmentSettings : Fragment() {
   private val binding by lazy {
       FragmentSettingsBinding.inflate(layoutInflater)
   }
    private val list = ArrayList<SettingsModel>()
    private val adapter by lazy {
        SettingsAdapter(list, requireActivity())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            settingsRecyclerview.adapter = adapter

            list.add(
                SettingsModel(
                    title = "How to use",
                    desc = "Know how to download statuses"
                )
            )
            list.add(
                SettingsModel(
                    title = "Save in Folder",
                    desc = "/internalstorage/Documents/${getString(R.string.app_name)}"
                )
            )
            list.add(
                SettingsModel(
                    title = "Disclaimer",
                    desc = "Read Our Disclaimer"
                )
            )
            list.add(
                SettingsModel(
                    title = "Privacy Policy",
                    desc = "Read Our Terms & Conditions"
                )
            )
            list.add(
                SettingsModel(
                    title = "Share",
                    desc = "Sharing is caring"
                )
            )
            list.add(
                SettingsModel(
                    title = "Rate Us",
                    desc = "Please support our work by rating on PlayStore"
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

}