package com.piyush.weatherofthecity.ui.forecastlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.piyush.weatherofthecity.R
import com.piyush.weatherofthecity.databinding.FragmentForecastListBinding
import com.piyush.weatherofthecity.di.modules.viewmodel.ViewModelFactory
import com.piyush.weatherofthecity.utils.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentForecastList: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ForecastListViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentForecastListBinding
    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    private val fragmentForecastListArgs: FragmentForecastListArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hourlyForecastAdapter = HourlyForecastAdapter {
            val directions = fragmentForecastListArgs.geoData.name?.let { city ->
                FragmentForecastListDirections.actionNavigationForecastListToNavigationForecastDetail(it,
                    city
                )
            }

            directions?.let { direction -> findNavController().navigate(direction) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListeners()
        initObserver()
        getForeCastList()
    }

    private fun initView(){
        binding.forecastRv.adapter = hourlyForecastAdapter
        binding.cityName = fragmentForecastListArgs.geoData.name
    }

    private fun initClickListeners(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver(){
        viewModel.hourlyForecastList.observe(viewLifecycleOwner,EventObserver{
            hourlyForecastAdapter.submitList(it)
        })

        viewModel.hourlyForecastError.observe(viewLifecycleOwner, EventObserver{
            Snackbar.make(requireView(), it.first, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun getForeCastList(){
        val geoData = fragmentForecastListArgs.geoData
        geoData.lat?.let {lat -> geoData.lon?.let { lon -> viewModel.getForecastList(lat, lon) } }
    }
}