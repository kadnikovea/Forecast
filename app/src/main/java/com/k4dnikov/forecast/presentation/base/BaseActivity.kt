package com.k4dnikov.forecast.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity(), BaseView {


    override fun showError(error: Int) {
        val err = resources.getString(error)
        if (!err.isEmpty())
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
        if (!error.isEmpty())
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
    }


    override fun hideLoading() {
    }


}