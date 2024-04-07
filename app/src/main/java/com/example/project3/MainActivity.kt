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

class MainActivity : AppCompatActivity() , TopFragment.OnSpinnerOptionSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onSpinnerItemSelected(data: Breed) {
        val bottomFragment = supportFragmentManager.findFragmentById(R.id.bottomFragment) as BottomFragment

        bottomFragment.updateCatName(data.name)
        bottomFragment.updateCatTemperament(data.temperament)
        bottomFragment.updateCatOrigin(data.origin)

        if (data.image != null) {
            bottomFragment.updateImage(data.image.width, data.image.height, data.image.url)
        }
    }

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