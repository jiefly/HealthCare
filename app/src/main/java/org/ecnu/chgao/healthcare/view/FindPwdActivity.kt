package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.bean.UserAction
import org.ecnu.chgao.healthcare.util.Config
import org.json.JSONException
import org.json.JSONObject

class FindPwdActivity : AppCompatActivity() {
    var TAG = "FindPwdActivity"
    var phoneET: EditText? = null
    var pwdET: EditText? = null
    var smsET: EditText? = null
    var getSmsCodeBtn: Button? = null
    var findPwdBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pwd)
        initView()
    }

    private fun initView() {
        phoneET = findViewById(R.id.id_find_pwd_phone) as EditText
        pwdET = findViewById(R.id.id_find_pwd_new_pwd) as EditText
        smsET = findViewById(R.id.id_find_pwd_code) as EditText
        getSmsCodeBtn = findViewById(R.id.id_find_pwd_get_sms_code) as Button
        getSmsCodeBtn?.setOnClickListener {
            val action = UserAction(this)
            try {
                action.getSmsCode(phoneET!!.text.toString(), Config.ACTION_GET_SMS_CODE, { Log.i(TAG, "send sms code success") }
                ) { _, _ -> }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        findPwdBtn = findViewById(R.id.id_find_pwd_btn) as Button
        findPwdBtn!!.setOnClickListener {
            val action = UserAction(this)
            action.findPwd(phoneET!!.text.toString(), pwdET!!.text.toString(), smsET!!.text.toString(), Config.ACTION_CHANGE_PASSWORD, {
                val result = JSONObject(it)
                if ("success" == result.getString("result")) {
                    Log.i(TAG, "change pwd success")
                } else {
                    Log.e(TAG, result.getString("result"))
                }
            }, { status, reason ->
                Log.i(TAG, "status:$status,reason:$reason")
            })
        }
    }
}
