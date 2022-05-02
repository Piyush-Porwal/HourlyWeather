package com.piyush.weatherofthecity.ui.weatherlookup

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.weatherofthecity.data.weather.WeatherRepository
import com.piyush.weatherofthecity.data.weather.model.GeoData
import com.piyush.weatherofthecity.network.NoInternetException
import com.piyush.weatherofthecity.utils.Constants
import com.piyush.weatherofthecity.utils.Event
import com.piyush.weatherofthecity.utils.async.ThreadManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class WeatherLookupViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val threadManager: ThreadManager
) : ViewModel() {

    private var _geoData = MutableLiveData<Event<GeoData>>()
    val geoData: LiveData<Event<GeoData>> = _geoData

    private var _geoDataError = MutableLiveData<Event<Pair<String, Int>>>()
    val geoDataError: LiveData<Event<Pair<String, Int>>> = _geoDataError

    var didTapLookup = ObservableBoolean(false)

    fun getGeoData(city: String) {
        viewModelScope.launch(threadManager.io) {
            val (response, statusCode) = try {
                weatherRepository.getGeoDataList(city = city)
            } catch (e: HttpException) {
                null to e.code()
            } catch (e: NoInternetException) {
                null to Constants.RESPONSE_1001_CONNECTION_FAILURE
            } catch (e: Exception) {
                null to 0
            }

            when (statusCode) {
                Constants.RESPONSE_200_SUCCESS -> {
                    if (response?.isNotEmpty() == true) response.let { geoData ->
                       _geoData.postValue(Event(geoData[0]))
                    } else {
                        _geoDataError.postValue(Event("Unable to Found City" to Constants.EMPTY_LIST_ERROR))
                    }
                }
                Constants.RESPONSE_1001_CONNECTION_FAILURE -> {
                    _geoDataError.postValue(Event("No Internet Connection" to Constants.RESPONSE_1001_CONNECTION_FAILURE))
                }
                else -> {
                    _geoDataError.postValue(Event("Something Went Wrong." to Constants.RESPONSE_1001_CONNECTION_FAILURE))
                }
            }
        }
    }
}