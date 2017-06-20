package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.RegisterPresent

open class RegisterActivity : BaseActivity(), RegisterViewer {
    val TAG = "RegisterActivity"
    var phoneET: EditText? = null
    var pwdET: EditText? = null
    var smsCodeET: EditText? = null
    var getSmsCodeTV: TextView? = null
    var registerTV: TextView? = null
    var present: RegisterPresent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        useCustomToolbar(title = getToolbarTitle())
        initView()
        present = RegisterPresent(this)
    }

    open protected fun getToolbarTitle(): String {
        return "注册"
    }

    open protected fun getLayoutRes(): Int {
        return R.layout.activity_register
    }

    private fun initView() {
        phoneET = findViewById(R.id.id_register_phone) as EditText?
        pwdET = findViewById(R.id.id_register_password) as EditText?
        smsCodeET = findViewById(R.id.id_register_sms_code) as EditText?
        getSmsCodeTV = findViewById(R.id.id_register_get_sms_code) as TextView?
        getSmsCodeTV?.setOnClickListener {
            present!!.onGetSmsCodeClick(phoneET?.text.toString())
        }
        registerTV = findViewById(R.id.id_register_register) as TextView?
        registerTV?.setOnClickListener {
            present!!.onRegisterClick(phoneET?.text.toString(), smsCodeET?.text.toString(), pwdET?.text.toString())
        }
    }

    override fun onRegisterSuccess() {
        showToast("注册成功")
        navigate<LoginActivity>()
    }

    override fun onRegisterFailed(msg: String?) {
        showAlertDialog("注册失败", msg, null, null)
    }

    override fun getContext(): Context {
        return this
    }
}
