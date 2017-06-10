package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import org.ecnu.chgao.healthcare.R

class UserInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        useCustomToolbar(title = "设置用户信息")
    }


}
