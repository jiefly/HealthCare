package org.ecnu.chgao.healthcare.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.ecnu.chgao.healthcare.R

abstract class BaseActivity : AppCompatActivity() {
    inline fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    inline fun showSnack(view: View, message: String?) {
        Snackbar.make(view, message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    inline fun getDecoration(bg: Int = R.drawable.div_bg): DividerItemDecoration {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(resources.getDrawable(bg))
        return decoration
    }

    inline fun showAlertDialog(title: String?, message: String?, positive: DialogInterface.OnClickListener?, negative: DialogInterface.OnClickListener?) {
        val builder = AlertDialog.Builder(this)
        if (!title.isNullOrEmpty())
            builder.setTitle(title)
        if (!message.isNullOrEmpty())
            builder.setMessage(message)
        builder.setNegativeButton("取消", negative)
        builder.setPositiveButton("确定", positive)
        builder.show()
    }

    inline fun <reified T : Activity> Activity.navigate(bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, T::class.java)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /*
    * should invoke this method after #setContentView()
    * toolbar layout should contain left icon(id:id_tool_bar_left_arrow) and title text view(id:id_tool_bar_title)
    * */
    inline fun useCustomToolbar(layoutId: Int = R.id.id_normal_tool_bar, title: String, onLeftIconClick: View.OnClickListener = View.OnClickListener { onBackPressed() }) {
        val toolbar = findViewById(layoutId) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        (toolbar.findViewById(R.id.id_tool_bar_title) as TextView).text = title
        toolbar.findViewById(R.id.id_tool_bar_left_arrow).setOnClickListener { onLeftIconClick.onClick(it) }
    }

    inline fun log(tag: String = "default", msg: String, pr: Int = 0) {
        when (pr) {
            0 -> Log.i(tag, msg)
            1 -> Log.w(tag, msg)
            else -> Log.e(tag, msg)
        }
    }
}
