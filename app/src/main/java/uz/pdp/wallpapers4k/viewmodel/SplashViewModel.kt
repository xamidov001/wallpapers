package uz.pdp.wallpapers4k.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.pdp.wallpapers4k.network.NetworkHelper
import uz.pdp.wallpapers4k.repository.PagingDataSource
import uz.pdp.wallpapers4k.repository.SplashRepository
import uz.pdp.wallpapers4k.retro.ApiClient
import uz.pdp.wallpapers4k.utils.SplashResource
import uz.pdp.wallpapers4k.utils.SplashResourceRandom

class SplashViewModel(private val networkHelper: NetworkHelper): ViewModel() {

    private val repository = SplashRepository(ApiClient.appService)

    fun fetchSplash(query: String, page: Int): LiveData<SplashResource> {
        val liveData = MutableLiveData<SplashResource>()
        if (networkHelper.isNetworkConnect()) {
            viewModelScope.launch {
                liveData.postValue(SplashResource.Loading)
                repository.getSplash(query, page)
                    .catch {
                        liveData.postValue(SplashResource.Error("Error"))
                    }
                    .collect {
                        if (it.isSuccessful) {
                            liveData.postValue(SplashResource.Success(it.body()!!))
                        }
                    }
            }
        } else {
            liveData.postValue(SplashResource.Error("Problem with internet"))
        }

        return liveData
    }

    fun fetchSplashRandom(): LiveData<SplashResourceRandom> {
        val liveData = MutableLiveData<SplashResourceRandom>()
        if (networkHelper.isNetworkConnect()) {
            liveData.postValue(SplashResourceRandom.Loading)
            viewModelScope.launch {
                Pager(PagingConfig(10)) {
                    PagingDataSource()
                }.flow.cachedIn(viewModelScope).catch {
                    liveData.postValue(SplashResourceRandom.Error("Error"))
                }.collect {
                    liveData.postValue(SplashResourceRandom.Success(it))
                }
            }
        } else {
            liveData.postValue(SplashResourceRandom.Error("Problem with internet"))
        }

        return liveData
    }

}