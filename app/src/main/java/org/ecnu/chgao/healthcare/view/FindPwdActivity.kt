package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.FindPwdPresent

class FindPwdActivity : RegisterActivity(), FindPwdViewer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        present = FindPwdPresent(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_find_pwd
    }

    override fun getToolbarTitle(): String {
        return "重置密码"
    }

    override fun onFindPwdFailed(msg: String?) {
        onRegisterFailed(msg)
    }

    override fun onFindPwdSuccess() {
        onRegisterSuccess()
    }

}
