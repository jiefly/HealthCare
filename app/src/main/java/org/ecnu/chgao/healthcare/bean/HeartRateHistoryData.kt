package com.example.jiefly.multiparametermonitor.measuring.data.historydata

import java.util.*

/**
 * Created by chgao on 17-5-25.
 */
class HeartRateHistoryData : NormalHistoryData<Int>() {
    fun setDate(date: Date): HeartRateHistoryData {
        this.date = date
        return this
    }

    fun setValue(value: Int): HeartRateHistoryData {
        this.value = value
        return this
    }
}