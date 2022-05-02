package com.piyush.weatherofthecity.ui.forecastlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.piyush.weatherofthecity.data.weather.model.HourlyForecastData
import com.piyush.weatherofthecity.databinding.ItemForecastBinding
import com.piyush.weatherofthecity.utils.DataTimeUtil

class HourlyForecastAdapter(private val clickListener: (HourlyForecastData) -> Unit): ListAdapter<HourlyForecastData,HourlyForecastAdapter.HourlyForeCastViewHolder>(HourlyForecastDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForeCastViewHolder {
        return HourlyForeCastViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HourlyForeCastViewHolder, position: Int) {
        val hourlyForecastData = getItem(position)
        holder.bind(hourlyForecastData,clickListener)
    }

    class HourlyForeCastViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            hourlyForecastData: HourlyForecastData, clickListener: (HourlyForecastData) -> Unit
        ) {
            binding.apply {
                this.hourlyForecastData = hourlyForecastData
                this.time = hourlyForecastData.dt?.let {
                    DataTimeUtil.convertEpochTimeToDateString("d MMM hh a",
                        it
                    )
                }
                executePendingBindings()
                root.setOnClickListener {
                    clickListener.invoke(hourlyForecastData)
                }
            }
        }
    }
}

class HourlyForecastDiffCallBack: DiffUtil.ItemCallback<HourlyForecastData>(){
    override fun areItemsTheSame(oldItem: HourlyForecastData, newItem: HourlyForecastData): Boolean {
        return oldItem.dt == newItem.dt
    }
    override fun areContentsTheSame(oldItem: HourlyForecastData, newItem: HourlyForecastData): Boolean {
        return oldItem == newItem
    }
}