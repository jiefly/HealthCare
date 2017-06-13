package org.ecnu.chgao.healthcare.bean

import com.litesuits.orm.db.annotation.Column
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.enums.AssignType

/**
 * Created by chgao on 17-6-13.
 */
class AlarmData {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var id: Int? = null
    @Column("type")
    var type: Int? = null
    @Column("remind_name")
    var remindName: String? = null
    @Column("remind_addition")
    var remindAddition: String? = null
    /**
     * @param flag            周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
     *                        表示按周每周提醒的闹钟（一周的周期性时间间隔）
     * @param hour            时
     * @param minute          分
     * @param id              闹钟的id
     * @param week            week=0表示一次性闹钟或者按天的周期性闹钟，非0 的情况下是几就代表以周为周期性的周几的闹钟
     * @param tips            闹钟提示信息
     * @param soundOrVibrator 2表示声音和震动都执行，1表示只有铃声提醒，0表示只有震动提醒
     */
    @Column("flag")
    var flag: Int? = null
    @Column("hour")
    var hour: Int? = null
    @Column("minute")
    var minute: Int? = null
    @Column("week")
    var week: Int? = null
    @Column("tips")
    var tips: String? = null
    @Column("remind_type")
    var remindType: Int? = null
}