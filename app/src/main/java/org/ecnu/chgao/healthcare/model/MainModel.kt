package org.ecnu.chgao.healthcare.model

import android.content.Context
import android.util.Log
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.application.BaseApplication
import org.ecnu.chgao.healthcare.bean.LocationUploadBean
import org.ecnu.chgao.healthcare.bean.NormalMainItemData
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.step.bean.StepData
import org.ecnu.chgao.healthcare.step.service.StepService
import org.ecnu.chgao.healthcare.util.DbUtils
import org.ecnu.chgao.healthcare.util.getTodayDate

/**
 * Created by chgao on 17-5-29.
 */
class MainModel(val present: MainPresent) : BaseModel<MainPresent>(present) {
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

    val spUtil = (present.applicationContext as BaseApplication).sharedPreferencesUtils!!
    var todayTask: Int

    init {
        todayTask = (spUtil.getParam(TODAY_TASK_STEP, TODAY_TASK_STEP_DEFAULT) as String).toInt()
        //init db
        initDb()
    }

    private fun initDb() {
        if (DbUtils.getLiteOrm(StepService.DB_NAME) == null) {
            DbUtils.createDb(present.applicationContext, StepService.DB_NAME)
        }
    }

    fun updateTodayTask(todayStep: Int) {
        this.todayTask = todayTask
        spUtil.setParam(TODAY_TASK_STEP, todayTask.toString())
    }

    fun getCount(): String {
        return spUtil.getParam(LoginModel.ACCOUNT_KEY, "13188888888") as String
    }
    fun updateCurrentStep(step: Int) {
        todayTask = step
        spUtil.setParam(TODAY_STEP, step.toString())
    }

    fun storeLocation(location: LocationUploadBean) {
        DbUtils.insert(location, StepService.DB_NAME)
    }

    fun getAllCards(): List<NormalMainItemData> {
        val mCards = ArrayList<NormalMainItemData>()
        mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.STEP).setmCurrentStep(getCurrentStep()).setmTotalStep(todayTask))
        if (spUtil.getParam(EditCardModel.CardType.FALL_DOWN.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FALL_DOWN).setmItemTitle("跌倒检测").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("跌到检测服务正在后台运行。点击可配置相关信息"))
        if (spUtil.getParam(EditCardModel.CardType.NOTIFICATION.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.NOTIFICATION).setmItemTitle("每日提醒").setmIconRes(R.drawable.ic_snooze_white_24dp).setmContent("每一个用户设置的提醒都应该显示为一条Item"))
        if (spUtil.getParam(EditCardModel.CardType.EVERY_DAY_HEALTH.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.EVERY_DAY_HEALTH).setmItemTitle("健康小提示").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("这里的消息可以来自于服务端的推送。"))
        if (spUtil.getParam(EditCardModel.CardType.LOCATION.value, true) as Boolean)
            mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.LOCATION).setmItemTitle("运动轨迹").setmIconRes(R.drawable.ic_place_white_24dp).setmContent("点击查看运动轨迹"))
        mCards.add(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FOTTER))
        return mCards
    }

    fun disableCard(cardType: EditCardModel.CardType) {
        spUtil.setParam(cardType.value, false)
    }

    fun clearPwd() {
        spUtil.remove(LoginModel.PWD_KEY)
    }

    fun getCurrentStep(): Int {
        initDb()
        val list = DbUtils.getQueryByWhere(StepData::class.java, "today", arrayOf(getTodayDate()), StepService.DB_NAME)
        if (list.size == 0 || list.isEmpty()) {
            Log.v(TAG, "未查询到当日步数信息")
            return 0
        } else if (list.size == 1) {
            Log.v(TAG, "StepData=" + list[0].toString())
            return Integer.parseInt(list[0].step)
        } else {
            Log.v(TAG, "出错了！")
            return 0
        }

    }
}