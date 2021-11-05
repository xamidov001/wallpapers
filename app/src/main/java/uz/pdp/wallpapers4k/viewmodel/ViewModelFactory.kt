package uz.pdp.wallpapers4k.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.pdp.wallpapers4k.network.NetworkHelper

class ViewModelFactory(private val networkHelper: NetworkHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(networkHelper) as T
        }
        throw Exception("Error")
    }
}