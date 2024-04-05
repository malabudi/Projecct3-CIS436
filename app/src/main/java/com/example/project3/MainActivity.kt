package com.example.project3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.project3.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() , TopFragment.OnSpinnerOptionSelectedListener{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //-------Added code-----------//
        binding.btnGetCatData.setOnClickListener {
            printCatData() //call this other function
        }
        //-------------------------------//
    }

    override fun onSpinnerItemSelected(data: String) {
        val bottomFragment = supportFragmentManager.findFragmentById(R.id.bottomFragment) as BottomFragment

        bottomFragment.updateTextView(data)
    }
    // method to interact with API
    fun printCatData() {
        var catUrl = "https://api.thecatapi.com/v1/breeds" +
                "?api_key=live_Z0JoMXajSmpJIXKFp3yFqKfuq9Z3Xk06C7TVpOmHONmOoHG7pNmtotGV7I7VabN7"

        val queue = Volley.newRequestQueue(this)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                var catsArray : JSONArray = JSONArray(response)
                //indices from 0 through catsArray.length()-1
                for(i in 0 until catsArray.length()) {
                    //${} is to interpolate the string /
// uses a string template
                    var theCat : JSONObject = catsArray.getJSONObject(i)
                    //now get the properties we want: name and description
                    Log.i("MainActivity", "Cat name: ${theCat.getString("name")}")
                    Log.i("MainActivity", "Cat description:${theCat.getString("description")}")
                }//end for
            },
            {
                Log.i("MainActivity", "That didn't work!")
            })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }//end printCatData

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}