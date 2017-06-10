package org.ecnu.chgao.healthcare.model

import android.content.Context
import org.ecnu.chgao.healthcare.util.SharedPreferencesUtils

/**
 * Created by chgao on 17-5-29.
 */
class MainModel(context: Context) : BaseModel() {
    companion object {
        @JvmField
        val TODAY_TASK_STEP = "today_task_step"
        @JvmField
        val TODAY_TASK_STEP_DEFAULT = "7000"
        @JvmField
        val TODAY_STEP = "today_step"
        @JvmField
        val TODAY_STEP_DEFAULT = "0"
    }

    val spUtil = SharedPreferencesUtils(context.applicationContext)
    var todayTask: Int
    var currentStep: Int

    init {
        todayTask = (spUtil.getParam(TODAY_TASK_STEP, TODAY_TASK_STEP_DEFAULT) as String).toInt()
        currentStep = (spUtil.getParam(TODAY_STEP, TODAY_STEP_DEFAULT) as String).toInt()
    }

    fun updateTodayTask(todayStep: Int) {
        this.todayTask = todayTask
        spUtil.setParam(TODAY_TASK_STEP, todayTask.toString())
    }

    fun updateCurrentStep(step: Int) {
        todayTask = step
        spUtil.setParam(TODAY_STEP, step.toString())
    }


}