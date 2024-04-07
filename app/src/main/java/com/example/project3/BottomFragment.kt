package com.example.project3

import android.R.attr.src
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project3.databinding.FragmentBottomBinding
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class BottomFragment : Fragment() {
    private lateinit var binding: FragmentBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateCatName(name: String) {
        binding.breedNameTextView.text = "Breed: " + name
    }

    fun updateCatTemperament(temperament: String) {
        binding.breedTemperamentTextView.text = "Temperament: " + temperament

    }

    fun updateCatOrigin(origin: String) {
        binding.breedOriginTextView.text = "Origin: " + origin
    }

    fun updateImage(width: Int, height: Int, url: String) {
        binding.catIV.layoutParams.width = width
        binding.catIV.layoutParams.height = height

        Picasso.get()
            .load(url)
            .into(binding.catIV);
    }
}