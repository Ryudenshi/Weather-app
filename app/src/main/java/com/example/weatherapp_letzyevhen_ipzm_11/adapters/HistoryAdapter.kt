package com.example.weatherapp_letzyevhen_ipzm_11.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_letzyevhen_ipzm_11.models.HistoryItem
import com.example.weatherapp_letzyevhen_ipzm_11.R // Import your R file for resource references

class HistoryAdapter(private val historyList: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind data to views in the ViewHolder if needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_history_activity, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        // Bind data to views in the ViewHolder if needed
        // Use historyList[position] to access the HistoryItem at the current position
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}