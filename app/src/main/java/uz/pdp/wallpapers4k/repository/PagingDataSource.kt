package uz.pdp.wallpapers4k.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import uz.pdp.wallpapers4k.classes.random.RandomClass
import uz.pdp.wallpapers4k.retro.ApiClient

class PagingDataSource: PagingSource<Int, RandomClass>() {
    private val photoRepository = SplashRepository(ApiClient.appService)

    override fun getRefreshKey(state: PagingState<Int, RandomClass>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RandomClass> {
        try {
            val currentPage = params.key ?: 1

            var loadResult: LoadResult.Page<Int, RandomClass>? = null

            if (params.key ?: 1 >= 1) {
                photoRepository.getSplashRandom(currentPage)
                    .catch {
                        loadResult = LoadResult.Page(
                            emptyList(),
                            currentPage - 1, currentPage + 1
                        )
                    }.collect {
                        if (it.isSuccessful) {
                            loadResult =
                                LoadResult.Page(
                                    it.body()!!,
                                    currentPage - 1, currentPage + 1
                                )
                        }
                    }
            } else {
                loadResult =
                    LoadResult.Page(
                        emptyList(),
                        null, currentPage + 1
                    )
            }
            return loadResult!!
        } catch (e: Exception) {
            return LoadResult.Error(e.fillInStackTrace())
        }
    }
}