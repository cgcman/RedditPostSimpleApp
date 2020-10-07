package com.grdj.devigettest.resources

import android.content.Context
import com.grdj.devigettest.R

class ResourcesProviderImpl(val context: Context) : ResourcesProvider{
    override fun getNotConnectedMessage() = context.resources.getString(R.string.NOT_CONNECTED_MESSAGE)
    override fun getApiError() = context.resources.getString(R.string.API_ERROR)
}