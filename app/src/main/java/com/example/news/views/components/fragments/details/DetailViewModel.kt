package com.example.news.views.components.fragments.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.network.ApiResponse
import com.example.news.network.RestApi
import com.example.news.network.RestApiFactory
import com.google.gson.JsonElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    var responseLiveData = MutableLiveData<ApiResponse<JsonElement>>()
    var apiResponse: ApiResponse<JsonElement>? = null

    private var restApi: RestApi? = null
    private var subscription: Disposable? = null

    init {
        restApi = RestApiFactory.create()
        apiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)

    }

    fun articleDetailApi(articlesId: String) {
        subscription = restApi!!.getArticlesDetail(articlesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                responseLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    responseLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    responseLiveData.postValue(apiResponse!!.error(throwable))
                }
            )
    }


    fun disposeSubscriber() {
        if (subscription != null)
            subscription!!.dispose()
    }
}