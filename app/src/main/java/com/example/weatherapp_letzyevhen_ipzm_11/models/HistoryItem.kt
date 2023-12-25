package com.example.weatherapp_letzyevhen_ipzm_11.models

import org.json.JSONArray

data class HistoryItem(
    val cityName: String,
    val requestTime: Long
) {
    companion object {
        fun fromJson(jsonString: String): MutableList<HistoryItem> {
            val historyList = mutableListOf<HistoryItem>()
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val cityName = jsonObj.getString("name")
                val requestTime = jsonObj.getLong("timestamp")
                val historyItem = HistoryItem(cityName, requestTime)
                historyList.add(historyItem)
            }
            return historyList
        }
    }
}
