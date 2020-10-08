package com.grdj.devigettest.util.network

interface NetworkManager {
    fun isConnected(): Boolean
    fun notConnectedMessage(message: String)
}