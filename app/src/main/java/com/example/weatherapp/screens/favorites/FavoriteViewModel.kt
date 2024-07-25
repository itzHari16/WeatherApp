package com.example.weatherapp.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favlist = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getfavorites().distinctUntilChanged()
                .collect { listoffav ->
                    if (listoffav.isNullOrEmpty()) {
                        Log.d("Empty", ":Empty favs")
                    } else {
                        _favList.value = listoffav
                        Log.d("FAVS", ":${favlist.value} ")
                    }
                }
        }
    }

    fun insertfavorite(fovorite: Favorite) = viewModelScope.launch {
        repository.insertfavorite(favorite = fovorite)
    }

    fun updatefavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite = favorite)
    }

    fun deletefavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite = favorite)
    }
}



