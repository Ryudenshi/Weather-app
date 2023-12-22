package com.example.weatherapp_letzyevhen_ipzm_11.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_letzyevhen_ipzm_11.R
import com.example.weatherapp_letzyevhen_ipzm_11.adapters.HistoryAdapter
import com.example.weatherapp_letzyevhen_ipzm_11.models.HistoryItem
import com.google.android.material.button.MaterialButton

class HistoryActivity : Fragment() {

    private lateinit var buttonNavigateFragmentMain: MaterialButton
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyList: MutableList<HistoryItem>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_history_activity, container, false)
        buttonNavigateFragmentMain = view.findViewById(R.id.buttonNavigateFragmentMain)
        historyRecyclerView = view.findViewById(R.id.history_recycler_view)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        historyList = loadHistoryList()

        historyAdapter = HistoryAdapter(historyList)
        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        historyRecyclerView.adapter = historyAdapter

        buttonNavigateFragmentMain.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_historyActivity_to_mainFragment)
        }

        return view
    }

    private fun loadHistoryList(): MutableList<HistoryItem> {
        val historyJson = sharedPreferences.getString("history", null)
        return if (historyJson != null) {
            HistoryItem.fromJson(historyJson)
        } else {
            mutableListOf()
        }
    }
}
