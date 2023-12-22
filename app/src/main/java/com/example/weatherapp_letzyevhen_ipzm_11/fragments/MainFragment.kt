package com.example.weatherapp_letzyevhen_ipzm_11.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp_letzyevhen_ipzm_11.R
import com.google.android.material.button.MaterialButton
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainFragment : Fragment() {

    private val API: String = "8247137ac6330997ce14d5e1f985bcc5"
    private val PREFS_KEY = "MyPrefs"
    private val CITY_KEY = "CityKey"
    private lateinit var citySpinner: Spinner
    private lateinit var citiesInUkraine: Array<String>
    private lateinit var loaderProgressBar: ProgressBar
    private lateinit var mainContainerLayout: RelativeLayout
    private lateinit var errorTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var updatedAtTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var buttonNavigateFragmentHistory: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        loaderProgressBar = view.findViewById(R.id.loader)
        mainContainerLayout = view.findViewById(R.id.mainContainer)
        errorTextView = view.findViewById(R.id.errortext)
        addressTextView = view.findViewById(R.id.address)
        updatedAtTextView = view.findViewById(R.id.updated_at)
        statusTextView = view.findViewById(R.id.status)
        buttonNavigateFragmentHistory = view.findViewById(R.id.buttonNavigateFragmentHistory)
        citySpinner = view.findViewById(R.id.citySpinner)
        citiesInUkraine = arrayOf("Kivertsi", "Lutsk", "Kyiv", "Lviv", "Odesa", "Kharkiv", "Dnipro", "Zaporizhzhia")

        val sharedPrefs = requireActivity().getSharedPreferences(PREFS_KEY, android.content.Context.MODE_PRIVATE)
        val selectedCity = sharedPrefs.getString(CITY_KEY, citiesInUkraine[0])

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, citiesInUkraine)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        if (selectedCity != null) {
            val position = citiesInUkraine.indexOf(selectedCity)
            if (position != -1) {
                citySpinner.setSelection(position)
                WeatherTask().execute(selectedCity)
            }
        }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = citiesInUkraine[position]

                val editor = sharedPrefs.edit()
                editor.putString(CITY_KEY, selectedCity)
                editor.apply()

                WeatherTask().execute(selectedCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }

        buttonNavigateFragmentHistory.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_historyActivity)
        }

        WeatherTask().execute(citiesInUkraine[0])
        return view
    }

    inner class WeatherTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            loaderProgressBar.visibility = View.VISIBLE
            mainContainerLayout.visibility = View.GONE
            errorTextView.visibility = View.GONE
        }

        override fun doInBackground(vararg selectedCity: String): String? {
            var response: String? = null

            try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=${selectedCity[0]},UA&units=metric&appid=$API")

                val urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }

                response = stringBuilder.toString()
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                if (result != null) {
                    val jsonObj = JSONObject(result)
                    val main = jsonObj.getJSONObject("main")
                    val sys = jsonObj.getJSONObject("sys")
                    val wind = jsonObj.getJSONObject("wind")
                    val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                    val updatedAt: Long = jsonObj.getLong("dt")
                    val updatedAtText = "Updated at: " + SimpleDateFormat(
                        "dd/MM/yyyy hh:mm a",
                        Locale.ENGLISH
                    ).format(Date(updatedAt * 1000))
                    val temp = main.getString("temp") + "°C"
                    val tempMin = "Min temp: " + main.getString("temp_min") + "°C"
                    val tempMax = "Max temp: " + main.getString("temp_max") + "°C"
                    val pressure = main.getString("pressure")
                    val humidity = main.getString("humidity")

                    val sunrise: Long = sys.getLong("sunrise")
                    val sunset: Long = sys.getLong("sunset")
                    val windSpeed = wind.getString("speed")
                    val weatherDescription = weather.getString("description")

                    addressTextView.text = jsonObj.getString("name") + ", " + sys.getString("country")
                    updatedAtTextView.text = updatedAtText
                    statusTextView.text = weatherDescription.capitalize()

                    loaderProgressBar.visibility = View.GONE
                    mainContainerLayout.visibility = View.VISIBLE
                } else {
                    loaderProgressBar.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = "Null response received"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loaderProgressBar.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = "Error: ${e.message}"
            }
        }
    }
}

