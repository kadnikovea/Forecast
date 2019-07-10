package com.k4dnikov.forecast.presentation.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.k4dnikov.forecast.R
import com.k4dnikov.forecast.common.GlideApp
import kotlinx.android.synthetic.main.forecast_fragment.view.*

class ForecastDialogFragment : DialogFragment() {


    var header: String? = null
    var description: String? = null
    var iconPath: String? = null

    companion object {

        private const val BUNDLE_HEADER: String = "bundleHeader"
        private const val BUNDLE_DESCRIPTION: String = "bundleDescription"
        private const val BUNDLE_ICON_PATH: String = "bundleIconPath"

        fun newInstance(header: String,
                       description: String,
                       iconPath: String): ForecastDialogFragment {
            val bundle = Bundle()
            bundle.putString(BUNDLE_HEADER, header)
            bundle.putString(BUNDLE_DESCRIPTION, description)
            bundle.putString(BUNDLE_ICON_PATH, iconPath)
            val forecastDialogFragment = ForecastDialogFragment()
            forecastDialogFragment.arguments = bundle
            return forecastDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        header = arguments?.getString(BUNDLE_HEADER)
        description = arguments?.getString(BUNDLE_DESCRIPTION)
        iconPath = arguments?.getString(BUNDLE_ICON_PATH)

        val view = LayoutInflater.from(context).inflate(R.layout.forecast_fragment, null)

        if(header != null)
            view.header.text = header
        if(description != null)
            view.description.text = description
        if(iconPath != null)
            GlideApp.with(this)
                .load(iconPath)
                .into(view.alertIcon)

        return AlertDialog.Builder(context!!)
            .setView(view)
            .create()
        
    }

}