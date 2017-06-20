package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.LoginPresent

class LoginActivity : BaseActivity(), LoginViewer {
    var accountET: EditText? = null
    var pwdET: EditText? = null
    var loginTv: TextView? = null
    var registerTv: TextView? = null
    var forgotPwdTv: TextView? = null
    var present: LoginPresent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        present = LoginPresent(this)
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

    override fun setAccountInfo(account: String, pwd: String) {
        account.isNullOrBlank().let {
            accountET!!.setText(account)
        }
        pwd.isNullOrBlank().let { pwdET!!.setText(pwd) }
    }

    //loginSuccess success will invoke this method
    override fun loginSuccess() {
        //check all needed permission
        checkPermission(CheckPermListener {
            showToast("登陆成功")
            navigate<MainActivity>()
            finish()
        }, R.string.ask_again, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun loginFailed(msg: String?) {
        showAlertDialog("登录失败", msg, null, null)
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
