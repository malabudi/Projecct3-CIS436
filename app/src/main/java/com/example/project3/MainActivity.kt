package com.example.project3

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.project3.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //-------Added code-----------//
        binding.btnGetCatData.setOnClickListener {
            printCatData() //call this other function
        }
        //-------------------------------//
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}