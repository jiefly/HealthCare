package org.ecnu.chgao.healthcare.model

import android.content.Context
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.bean.NormalMainItemData
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

    fun getAllCards(): List<NormalMainItemData> {
        val mCards = ArrayList<NormalMainItemData>()
        if (spUtil.getParam(EditCardModel.CardType.STEP.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.STEP))
        if (spUtil.getParam(EditCardModel.CardType.FALL_DOWN.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FALL_DOWN).setmItemTitle("跌倒检测").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("跌到检测服务正在后台运行。点击可配置相关信息"))
        if (spUtil.getParam(EditCardModel.CardType.NOTIFICATION.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.NOTIFICATION).setmItemTitle("每日提醒").setmIconRes(R.drawable.ic_snooze_white_24dp).setmContent("每一个用户设置的提醒都应该显示为一条Item"))
        if (spUtil.getParam(EditCardModel.CardType.EVETY_DAY_HEALTH.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.EVETY_DAY_HEALTH).setmItemTitle("健康小提示").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("这里的消息可以来自于服务端的推送。"))
        if (spUtil.getParam(EditCardModel.CardType.LOCATION.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.LOCATION).setmItemTitle("运动轨迹").setmIconRes(R.drawable.ic_place_white_24dp).setmContent("点击查看运动轨迹"))
        if (spUtil.getParam(EditCardModel.CardType.EDIT_CARD.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FOTTER))
        return mCards
    }

    fun disableCard(cardType: EditCardModel.CardType) {
        spUtil.setParam(cardType.value, false)
    }


}