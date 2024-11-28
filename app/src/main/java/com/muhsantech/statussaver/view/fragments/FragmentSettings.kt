package com.muhsantech.statussaver.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhsantech.statussaver.R
import com.muhsantech.statussaver.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment() {
   private val binding by lazy {
       FragmentSettingsBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apply {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

}