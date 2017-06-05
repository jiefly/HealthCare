package org.ecnu.chgao.healthcare.bean

import com.example.jiefly.multiparametermonitor.measuring.data.historydata.NormalHistoryData
import java.util.*

/**
 * Created by chgao on 17-5-25.
 */
class StepHistoryData : NormalHistoryData<Int>() {
    fun setDate(date: Date): StepHistoryData {
        this.date = date
        return this
    }

    fun setValue(value: Int): StepHistoryData {
        this.value = value
        return this
    }
}