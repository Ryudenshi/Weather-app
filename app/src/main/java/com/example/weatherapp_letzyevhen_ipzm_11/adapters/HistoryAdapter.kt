package com.example.weatherapp_letzyevhen_ipzm_11.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_letzyevhen_ipzm_11.models.HistoryItem
import com.example.weatherapp_letzyevhen_ipzm_11.R

class HistoryAdapter(private val historyList: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item_layout, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.itemView.findViewById<TextView>(R.id.textViewCityName).text = currentItem.cityName
        holder.itemView.findViewById<TextView>(R.id.textViewRequestTime).text = currentItem.requestTime.toString()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
