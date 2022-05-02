package com.piyush.weatherofthecity.ui.forecastDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.piyush.weatherofthecity.R
import com.piyush.weatherofthecity.databinding.FragmentForecastDetailBinding
import dagger.android.support.DaggerFragment

class FragmentForecastDetail: DaggerFragment(){

    private lateinit var binding: FragmentForecastDetailBinding
    private val fragmentForecastDetailArgs: FragmentForecastDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListeners()
    }

    private fun initView(){
        binding.cityName = fragmentForecastDetailArgs.cityName
        binding.hourlyForecastData = fragmentForecastDetailArgs.hourlyForecastData
    }

    private fun initClickListeners(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}