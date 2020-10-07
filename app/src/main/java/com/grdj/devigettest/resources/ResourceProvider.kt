package com.grdj.devigettest.resources

interface ResourcesProvider {
    fun getNotConnectedMessage() : String
    fun getApiError() : String
}