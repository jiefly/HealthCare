package org.ecnu.chgao.healthcare.bean

/**
 * Created by chgao on 17-6-12.
 */
class NormalRemindItemData(var name: String, var detail: String, var time: String) {
    var index: Int = 0
    var remindData: RemindData? = null
    var extention: String? = null
}