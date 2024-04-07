package com.example.project3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3.databinding.FragmentBottomBinding

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
        binding.breedNameTextView.text = name
    }

    fun updateCatTemperament(temperament: String) {
        binding.breedTemperamentTextView.text = temperament

    }

    fun updateCatOrigin(origin: String) {
        binding.breedOriginTextView.text = origin

    }
}