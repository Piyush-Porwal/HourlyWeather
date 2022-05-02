package com.piyush.weatherofthecity.ui.weatherlookup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.piyush.weatherofthecity.R
import com.piyush.weatherofthecity.databinding.FragmentWeatherLookupBinding
import com.piyush.weatherofthecity.di.modules.viewmodel.ViewModelFactory
import com.piyush.weatherofthecity.utils.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentWeatherLookup : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: WeatherLookupViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentWeatherLookupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_lookup,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initView()
        initObserver()
    }

    private fun initView() {
        binding.viewmodel = viewModel
    }

    private fun initClickListener(){
        binding.lookupBtn.setOnClickListener {
            with(binding){
                hideKeyBoard(lookupEdtxt)

                if (lookupEdtxt.text.toString().trim().isNotEmpty()){
                    viewModel.didTapLookup.set(true)
                    viewModel.getGeoData(lookupEdtxt.text.toString())
                }else {
                    Snackbar.make(requireView(), "Please enter a city name.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObserver(){
        viewModel.geoData.observe(viewLifecycleOwner, EventObserver{
            viewModel.didTapLookup.set(false)
            val directions = FragmentWeatherLookupDirections.actionNavigationWeatherLookupToNavigationForecastList(it)
            findNavController().navigate(directions)
        })

        viewModel.geoDataError.observe(viewLifecycleOwner, EventObserver{
            viewModel.didTapLookup.set(false)
            Snackbar.make(requireView(), it.first, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun hideKeyBoard(view: View){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}