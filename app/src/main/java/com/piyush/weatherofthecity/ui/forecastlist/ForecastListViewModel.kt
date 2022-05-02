package com.piyush.weatherofthecity.ui.forecastlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.weatherofthecity.data.weather.WeatherRepository
import com.piyush.weatherofthecity.data.weather.model.HourlyForecastData
import com.piyush.weatherofthecity.network.NoInternetException
import com.piyush.weatherofthecity.utils.Constants
import com.piyush.weatherofthecity.utils.Event
import com.piyush.weatherofthecity.utils.async.ThreadManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class ForecastListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val threadManager: ThreadManager
): ViewModel() {

    private var _hourlyForecastList = MutableLiveData<Event<List<HourlyForecastData>>>()
    val hourlyForecastList: LiveData<Event<List<HourlyForecastData>>> = _hourlyForecastList

    private var _hourlyForecastError = MutableLiveData<Event<Pair<String, Int>>>()
    val hourlyForecastError: LiveData<Event<Pair<String, Int>>> = _hourlyForecastError

    fun getForecastList(lat: Double, lon: Double) {
        viewModelScope.launch(threadManager.io) {
            val (response, statusCode) = try {
                weatherRepository.getWeatherForecastList(lat, lon)
            }catch (e: HttpException) {
                null to e.code()
            } catch (e: NoInternetException) {
                null to Constants.RESPONSE_1001_CONNECTION_FAILURE
            } catch (e: Exception) {
                null to 0
            }

            when (statusCode) {
                Constants.RESPONSE_200_SUCCESS -> response?.let { forecastList ->
                    if (forecastList.hourly?.isNotEmpty() == true){
                        _hourlyForecastList.postValue(Event(forecastList.hourly!!))
                    }else {
                        _hourlyForecastError.postValue(Event("No Forecast data found for this city" to Constants.EMPTY_LIST_ERROR))
                    }
                }
                Constants.RESPONSE_1001_CONNECTION_FAILURE -> {
                    _hourlyForecastError.postValue(Event("No Internet Connection" to Constants.RESPONSE_1001_CONNECTION_FAILURE))
                }
                else -> {
                    _hourlyForecastError.postValue(Event("Something Went Wrong." to Constants.RESPONSE_1001_CONNECTION_FAILURE))
                }
            }
        }
    }
}