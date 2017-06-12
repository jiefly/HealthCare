package org.ecnu.chgao.healthcare.view

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.TodoRvAdapter
import org.ecnu.chgao.healthcare.bean.NormalRemindItemData
import rx.android.schedulers.AndroidSchedulers

class TodoActivity : BaseActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: TodoRvAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        useCustomToolbar(title = "每日提醒")
        initRv()
    }

    private fun initRv() {
        recyclerView = findViewById(R.id.id_todo_rv) as RecyclerView?
        adapter = TodoRvAdapter(this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.addItemDecoration(getDecoration())
        recyclerView!!.adapter = adapter
        for (i in 0..10)
            adapter!!.addData(NormalRemindItemData("药物$i", "$i 片", "08:00"))
        adapter!!.notifyDataSetChanged()

        adapter!!.positionClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {

        }

        adapter!!.positionLongClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            showAlertDialog("删除当前提醒", null, DialogInterface.OnClickListener { _, _ -> adapter!!.removeData(it.index) }, DialogInterface.OnClickListener { _, _ -> })
        }
    }
}
