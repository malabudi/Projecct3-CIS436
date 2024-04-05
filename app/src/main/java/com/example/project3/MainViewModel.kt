package com.example.project3

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String
    // Consider adding more fields as necessary
)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _breedsList = MutableLiveData<List<Breed>>()
    val breedsList: LiveData<List<Breed>> = _breedsList
    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        var catUrl = "https://api.thecatapi.com/v1/breeds" +
                "?api_key=live_Z0JoMXajSmpJIXKFp3yFqKfuq9Z3Xk06C7TVpOmHONmOoHG7pNmtotGV7I7VabN7"
        // Get a RequestQueue
        val queue = Volley.newRequestQueue(getApplication<Application>().applicationContext)

        // Request a string response from the provided URL
        val stringRequest = StringRequest(Request.Method.GET, catUrl,
            { response ->
                try {
                    // Parse the JSON response
                    val jsonArray = JSONArray(response)
                    val breeds = mutableListOf<Breed>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val breed = Breed(
                            id = jsonObject.getString("id"),
                            name = jsonObject.getString("name"),
                            temperament = jsonObject.getString("temperament"),
                            origin = jsonObject.getString("origin")
                        )
                        breeds.add(breed)
                    }
                    // Update the LiveData with the fetched breeds
                    _breedsList.postValue(breeds)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Handle error - perhaps update LiveData with an error state
                }
            },
            {
                // Handle error
                Log.e("MainViewModel", "Failed to fetch breeds")
                // Perhaps update LiveData with an error state
            })

        // Add the request to the RequestQueue
        queue.add(stringRequest)
    }

}
