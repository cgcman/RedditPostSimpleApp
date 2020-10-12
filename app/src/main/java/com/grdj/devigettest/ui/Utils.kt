package com.grdj.devigettest.ui

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.grdj.devigettest.R
import com.grdj.devigettest.util.Constants
import java.util.concurrent.TimeUnit

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius= 30f
        start()
    }
}

fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun convertToTimeAgo(res: Resources, itemTime: Long): String {

    val currentMillis = System.currentTimeMillis()
    val itemMillis = TimeUnit.SECONDS.toMillis(itemTime)
    val diffTime = currentMillis - itemMillis

    if (itemTime > Constants.SIXTY) {
        val timeInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffTime)
        if (timeInMinutes > Constants.SIXTY) {
            val timeInHours = TimeUnit.MILLISECONDS.toHours(diffTime)
            if (timeInHours > Constants.A_DAY_IN_HOURS) {
                val timeInDays = TimeUnit.MILLISECONDS.toDays(diffTime)
                if (timeInDays > Constants.A_MONTH) {
                    val timeInMonths = timeInDays / Constants.A_MONTH
                    if (timeInMonths > Constants.A_YEAR) {
                        val timeInYears = timeInMonths / Constants.A_YEAR
                        return res.getString(R.string.item_time, timeInYears, Constants.YEARS)
                    } else {
                        return res.getString(R.string.item_time, timeInMonths, Constants.MONTHS)
                    }
                } else {
                    return res.getString(R.string.item_time, timeInDays, Constants.DAYS)
                }
            } else {
                return res.getString(R.string.item_time, timeInHours, Constants.HOURS)
            }
        } else {
            return res.getString(R.string.item_time, timeInMinutes, Constants.MINUTES)
        }
    } else {
        return res.getString(R.string.item_moment)
    }
}