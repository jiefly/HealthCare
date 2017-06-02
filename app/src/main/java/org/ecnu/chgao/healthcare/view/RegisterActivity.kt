package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import cn.jpush.sms.SMSSDK
import cn.jpush.sms.listener.SmscheckListener
import cn.jpush.sms.listener.SmscodeListener
import org.ecnu.chgao.healthcare.R

class RegisterActivity : BaseActivity(), RegisterViewer {
    val TAG = "RegisterActivity"
    var phoneET: EditText? = null
    var pwdET: EditText? = null
    var smsCodeET: EditText? = null
    var getSmsCodeTV: TextView? = null
    var registerTV: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        phoneET = findViewById(R.id.id_register_phone) as EditText?
        pwdET = findViewById(R.id.id_register_password) as EditText?
        smsCodeET = findViewById(R.id.id_register_sms_code) as EditText?
        getSmsCodeTV = findViewById(R.id.id_register_get_sms_code) as TextView?
        getSmsCodeTV?.setOnClickListener {
            SMSSDK.getInstance().getSmsCodeAsyn(phoneET?.text.toString(), "1", object : SmscodeListener {
                override fun getCodeSuccess(p0: String?) {
                    showToast("success:$p0")
                    log(TAG,"success:$p0")
                }

                override fun getCodeFail(p0: Int, p1: String?) {
                    showToast("fail:p0:$p0 p1:$p1")
                    log(TAG,"fail:p0:$p0 p1:$p1",3)
                }

            })
        }
        registerTV = findViewById(R.id.id_register_register) as TextView?
        registerTV?.setOnClickListener {
            SMSSDK.getInstance().checkSmsCodeAsyn(phoneET?.text.toString(), smsCodeET?.text.toString(),
                    object : SmscheckListener {
                        override fun checkCodeSuccess(p0: String?) {
                            showToast("验证成功,验证码信息:$p0")
                        }

                        override fun checkCodeFail(p0: Int, p1: String?) {
                            showToast("验证失败,错误码:$p0,错误信息:$p1")
                        }
                    })
        }
    }
}
