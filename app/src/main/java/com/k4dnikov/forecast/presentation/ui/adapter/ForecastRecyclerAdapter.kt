package com.k4dnikov.forecast.presentation.ui.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.k4dnikov.forecast.R
import com.k4dnikov.forecast.common.inflate
import com.k4dnikov.forecast.data.api.model.XEntity

class ForecastRecyclerAdapter() : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder>() {

    var hoursForecast: MutableList<XEntity>? = ArrayList<XEntity>()


    fun addData(newData: XEntity){
        hoursForecast?.add(newData)
        notifyItemInserted(hoursForecast!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRecyclerAdapter.ForecastHolder {
        val inflatedView = parent.inflate(R.layout.forecast_item)
        return ForecastHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return if(hoursForecast == null) 0
        else
            hoursForecast!!.size
    }

    override fun onBindViewHolder(holder: ForecastRecyclerAdapter.ForecastHolder, position: Int) {

    }

    class ForecastHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view = v
        private var forecast: XEntity? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(itemView: View?) {
            Log.d("XXXXXXXX "," Recycler click " + forecast.toString())


        }

    }

}