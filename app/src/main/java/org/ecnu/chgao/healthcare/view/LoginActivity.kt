package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import cn.jpush.sms.SMSSDK
import cn.jpush.sms.listener.SmscodeListener
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.LoginPresent

class LoginActivity : BaseActivity(), LoginViewer {

    var accountET: EditText? = null
    var pwdET: EditText? = null
    var loginTv: TextView? = null
    var registerTv: TextView? = null
    var forgotPwdTv: TextView? = null
    var present: LoginPresent? = null

    init {
        present = LoginPresent(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        accountET = findViewById(R.id.accountEdit) as EditText
        loginTv = findViewById(R.id.login_btn) as TextView
        loginTv?.setOnClickListener { present?.onLoginClick(accountET?.text.toString(), pwdET?.text.toString()) }
        pwdET = findViewById(R.id.pwdEdit) as EditText
        registerTv = findViewById(R.id.register) as TextView
        registerTv?.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        registerTv?.setOnClickListener { present?.onRegisterClick() }
        forgotPwdTv = findViewById(R.id.id_login_forgot_pw) as TextView
        forgotPwdTv?.setOnClickListener { present?.onForgotPdwClick() }
    }


    //login success will invoke this method
    override fun login() {
        showToast("登陆成功")
        navigate<MainActivity>()
    }

    override fun jumpToFindPwd() {
        navigate<FindPwdActivity>()
    }


    override fun jumpToRegister() {
        navigate<RegisterActivity>()
    }

    override fun getContext(): Context {
        return this
    }

}
