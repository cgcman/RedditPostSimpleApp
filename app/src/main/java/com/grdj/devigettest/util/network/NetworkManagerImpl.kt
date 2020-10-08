package com.grdj.devigettest.util.network

import android.content.Context
import com.grdj.devigettest.util.extensions.isConnected
import com.grdj.devigettest.util.extensions.showToast

class NetworkManagerImpl(val context: Context): NetworkManager {
    override fun isConnected() = context.isConnected()
    override fun notConnectedMessage(message : String) = context.showToast(message , message.length)
}