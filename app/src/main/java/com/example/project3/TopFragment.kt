package com.example.project3

import android.R
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project3.databinding.FragmentTopBinding
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TopFragment : Fragment() {

    private var _binding: FragmentTopBinding? = null

    private lateinit var spinnerOptionSelectedListener: OnSpinnerOptionSelectedListener

    interface OnSpinnerOptionSelectedListener {
        fun onSpinnerItemSelected(data : Breed)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            spinnerOptionSelectedListener = context as OnSpinnerOptionSelectedListener
        }
        catch (e : ClassCastException) {
            throw ClassCastException(context.toString()
                    + " Must Implement OnItemSelectedListener")
        }
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java] // Use requireActivity() for shared ViewModel

        // Observe the breedsList LiveData
        viewModel.breedsList.observe(viewLifecycleOwner, Observer { breeds ->
            // Extract the breeds from the breeds list
            val breedNames = breeds.map { it.name }
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, breedNames)
            binding.breedsSpinner.adapter = spinnerAdapter
        })

        binding.breedsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedBreed = viewModel.breedsList.value?.get(position)
                selectedBreed?.let {
                    spinnerOptionSelectedListener.onSpinnerItemSelected(it)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle the case where no breed is selected
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}