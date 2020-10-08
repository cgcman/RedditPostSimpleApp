package com.grdj.devigettest.ui

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

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