package com.terasoltechnologies.mvvm_test.networkdfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.terasoltechnologies.mvvm_test.Network.ApiStatus
import com.terasoltechnologies.mvvm_test.Network.MarsApi
import com.terasoltechnologies.mvvm_test.Network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class NetworkViewModel : ViewModel() {

    private var modelJob = Job()
    private var coroutine = CoroutineScope(modelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    private val _apiData = MutableLiveData<List<MarsProperty>>()

    val apiData: LiveData<List<MarsProperty>>
        get() = _apiData


    init {
        Timber.d("OnCreateHomeModel")
        getApiData()
    }

    private fun getApiData() {
        coroutine.launch {
            _status.value = ApiStatus.LOADING

            val getData = MarsApi.retrofitService.getProperties()
            try {

                _apiData.value = getData.await()
                _status.value = ApiStatus.SUCCESS

            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("OnCancelModel")

        modelJob.cancel()

    }
}