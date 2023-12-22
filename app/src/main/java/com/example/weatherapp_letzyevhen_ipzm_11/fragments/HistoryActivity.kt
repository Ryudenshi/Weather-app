package com.example.weatherapp_letzyevhen_ipzm_11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.weatherapp_letzyevhen_ipzm_11.R
import com.google.android.material.button.MaterialButton

class HistoryActivity : Fragment() {
    private lateinit var buttonNavigateFragmentMain: MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_history_activity, container, false)
        buttonNavigateFragmentMain = view.findViewById(R.id.buttonNavigateFragmentMain)

        buttonNavigateFragmentMain.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_historyActivity_to_mainFragment)
        }
        return view
    }
}