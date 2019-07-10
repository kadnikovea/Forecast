package com.k4dnikov.forecast.presentation.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.k4dnikov.forecast.common.inflate
import com.k4dnikov.forecast.common.recyclerDateFormat
import com.k4dnikov.forecast.data.api.model.XEntity
import kotlinx.android.synthetic.main.forecast_item.view.*


class ForecastRecyclerAdapter(val activity: Activity) : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder>() {

    var hoursForecast: MutableList<XEntity>? = ArrayList<XEntity>()


    fun addData(newData: XEntity){
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

    class ForecastHolder(v: View, val activity: Activity) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view = v
        private var forecast: XEntity? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(itemView: View?) {
            Log.d("XXXXXXXX "," Recycler click " + forecast.toString())
        }

        fun bind(xEntity: XEntity?) {

            view.itemDate.text = recyclerDateFormat(xEntity?.dt!!)

            view.itemTemperature.text = xEntity?.mainEntity?.temp.toString() + "К"
            val icon = xEntity.weatherEntity?.get(0)?.icon
            Glide.with(activity)
                .load("http://openweathermap.org/img/wn/$icon@2x.png")
                .into(view.itemImage)

        }

    }

}