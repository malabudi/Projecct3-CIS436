package com.example.project3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String
    // Consider adding more fields as necessary
)
class MainViewModel : ViewModel() {

    private val catBreedsNames = listOf("Breed 1", "Breed 2", "Breed 3") // Example list

    // Function to get the list of breed names
    fun getCatBreedNames(): List<String> {
        return catBreedsNames
    }
    private val _breedsList = MutableLiveData<List<Breed>>()
    val breedsList: LiveData<List<Breed>> = _breedsList

    private var _selectedBreed = MutableLiveData<Breed?>()
    val selectedBreed: LiveData<Breed?> = _selectedBreed


    fun fetchBreeds(): LiveData<List<Breed>>{
        // Here, you would make your API call and then update _breedsList.value with the result.
        return breedsList
    }

    fun setSelectedBreed(breed: Breed) {
        _selectedBreed.value = breed
    }

}