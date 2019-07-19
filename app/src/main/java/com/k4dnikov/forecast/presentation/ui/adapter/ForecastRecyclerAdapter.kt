package com.k4dnikov.forecast.presentation.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.k4dnikov.forecast.common.inflate
import com.k4dnikov.forecast.common.recyclerDateFormat
import com.k4dnikov.forecast.data.api.model.HourForecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import com.k4dnikov.forecast.presentation.ui.activity.MainActivity
import com.k4dnikov.forecast.presentation.ui.fragment.ForecastDialogFragment
import kotlinx.android.synthetic.main.forecast_item.view.*


class ForecastRecyclerAdapter(val activity: Activity) : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder>() {

    var hoursForecast: MutableList<HourForecastEntity>? = ArrayList<HourForecastEntity>()


    fun addData(newData: HourForecastEntity){
        hoursForecast?.add(newData)
        notifyItemInserted(hoursForecast!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRecyclerAdapter.ForecastHolder {
        val inflatedView = parent.inflate(com.k4dnikov.forecast.R.layout.forecast_item)
        return ForecastHolder(inflatedView, activity)
    }

    override fun getItemCount(): Int {
        return if(hoursForecast == null) 0
        else
            hoursForecast!!.size
    }

    override fun onBindViewHolder(holder: ForecastRecyclerAdapter.ForecastHolder, position: Int) {
        holder.bind(hoursForecast?.get(position))
    }

    class ForecastHolder(v: View, val activity: Activity) : RecyclerView.ViewHolder(v){

        private var view = v
        private var forecast: HourForecast? = null


        fun bind(hourEntity: HourForecastEntity?) {

            view.itemDate.text = recyclerDateFormat(hourEntity?.dt!!)
            view.itemTemperature.text = hourEntity?.mainEntity?.temp.toString() + "К"
            val icon = hourEntity.weatherEntity?.get(0)?.icon

            val pathToicon = "http://openweathermap.org/img/wn/$icon@2x.png"

            Glide.with(activity)
                .load(pathToicon)
                .into(view.itemImage)

            view.setOnClickListener{

                val dialog = ForecastDialogFragment.newInstance(hourEntity.weatherEntity?.get(0)?.main!!,
                    hourEntity.weatherEntity?.get(0)?.description!!,
                    pathToicon)

                dialog.show((activity as MainActivity).supportFragmentManager, ForecastDialogFragment::class.java.simpleName)

            }

        }

    }

}