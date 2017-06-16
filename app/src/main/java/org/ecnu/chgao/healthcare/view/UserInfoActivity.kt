package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.bean.UserInfo
import org.ecnu.chgao.healthcare.present.UserInfoPresent

class UserInfoActivity : BaseActivity(), UserInfoViewer {
    lateinit var present: UserInfoPresent
    lateinit var userNameEt: EditText
    lateinit var userSexRg: RadioGroup
    lateinit var emergencyNameEt: EditText
    lateinit var emergencyPhoneEt: EditText
    lateinit var emergencySexRg: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        useCustomToolbar(title = "设置用户信息", onRightIconClick = View.OnClickListener {
            //save changes here
            //info not complete
            if (userNameEt.text.toString().isNullOrBlank() || emergencyNameEt.text.toString().isNullOrBlank() || emergencyPhoneEt.text.toString().isNullOrBlank()) {
                showAlertDialog("请完善个人信息", "点击确认将继续完善个人信息", { _, _ -> }, { _, _ -> })
                return@OnClickListener
            } else {
                present.saveUserInfo(userNameEt.text.toString(), userSexRg.indexOfChild(findViewById(userSexRg.checkedRadioButtonId)), emergencyNameEt.text.toString(), emergencyPhoneEt.text.toString(), emergencySexRg.indexOfChild(findViewById(emergencySexRg.checkedRadioButtonId)))
            }
        })
        initView()
        present = UserInfoPresent(this)
    }

    override fun onBackPressed() {
        showAlertDialog("放弃保存个人信息", "点击确认放弃保存个人信息，点击取消继续修改个人信息", positive = { _, _ -> finish() }, negative = { _, _ -> })
    }

    private fun initView() {
        userNameEt = findViewById(R.id.id_user_info_name_et) as EditText
        userSexRg = findViewById(R.id.id_user_info_rg) as RadioGroup
        emergencyNameEt = findViewById(R.id.id_user_info_emergency_name_et) as EditText
        emergencyPhoneEt = findViewById(R.id.id_user_info_emergency_phone_et) as EditText
        emergencySexRg = findViewById(R.id.id_user_info_emergency_rg) as RadioGroup
    }


    override fun setUserInfo(userInfo: UserInfo) {
        userNameEt.setText(userInfo.userName)
        when (userInfo.userSex) {
            0 -> userSexRg.check(R.id.id_user_info_sex_male)
            else -> userSexRg.check(R.id.id_user_info_sex_female)
        }
        emergencyNameEt.setText(userInfo.emergencyName)
        emergencyPhoneEt.setText(userInfo.emergencyPhone)
        when (userInfo.userSex) {
            0 -> emergencySexRg.check(R.id.id_user_info_emergency_sex_male)
            else -> emergencySexRg.check(R.id.id_user_info_emergency_sex_female)
        }
    }

    override fun getContext(): Context {
        return this
    }
}
