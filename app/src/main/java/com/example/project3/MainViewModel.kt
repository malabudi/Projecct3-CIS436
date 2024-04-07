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

data class CatImg(
    val width: Int,
    val height: Int,
    val url: String
)

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val image: CatImg
)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _breedsList = MutableLiveData<List<Breed>>()
    val breedsList: LiveData<List<Breed>> = _breedsList
    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        val catUrl = "https://api.thecatapi.com/v1/breeds" +
                "?api_key=live_Z0JoMXajSmpJIXKFp3yFqKfuq9Z3Xk06C7TVpOmHONmOoHG7pNmtotGV7I7VabN7"
        // Get a RequestQueue
        val queue = Volley.newRequestQueue(getApplication<Application>().applicationContext)

        val stringRequest = StringRequest(Request.Method.GET, catUrl,
            { response ->
                try {
                    // Parse the JSON response
                    val jsonArray = JSONArray(response)
                    val breeds = mutableListOf<Breed>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.d("tag", jsonObject.toString())
                        val breed = Breed(
                            id = jsonObject.getString("id"),
                            name = jsonObject.getString("name"),
                            temperament = jsonObject.getString("temperament"),
                            origin = jsonObject.getString("origin"),
                            image = if (!jsonObject.isNull("image")) {
                                val imageObject = jsonObject.getJSONObject("image")
                                CatImg(
                                    width = imageObject.optInt("width", 0), // Default value of 0 if width is not present
                                    height = imageObject.optInt("height", 0), // Default value of 0 if height is not present
                                    url = imageObject.optString("url", "") // Default value of empty string if url is not present
                                )
                            } else {
                                // If "image" is null in the JSON, set image to a default CatImg with default values
                                CatImg(width = 0, height = 0, url = "")
                            }
                        )

                        breeds.add(breed)
                    }
                    // Update the LiveData with the fetched breeds
                    _breedsList.postValue(breeds)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {
                Log.e("MainViewModel", "Failed to fetch breeds")
            })

        // Add the request to the RequestQueue
        queue.add(stringRequest)
    }

}
