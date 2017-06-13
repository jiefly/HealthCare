package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionButton
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.TodoRvAdapter
import org.ecnu.chgao.healthcare.bean.NormalRemindItemData
import org.ecnu.chgao.healthcare.present.ToDoPresent
import rx.android.schedulers.AndroidSchedulers

class TodoActivity : BaseActivity(), ToDoViewer {
    var recyclerView: RecyclerView? = null
    var adapter: TodoRvAdapter? = null
    var present: ToDoPresent? = null
    var floatActionButton: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        useCustomToolbar(title = "每日提醒")
        initView()
        present = ToDoPresent(this)
    }

    private fun initView() {

        initRv()
        floatActionButton = findViewById(R.id.id_remind_float_button) as FloatingActionButton?
        floatActionButton!!.setOnClickListener {
            present!!.handleAddClick()
            navigate<NotificationEditActivity>()
        }
    }

    private fun initRv() {
        recyclerView = findViewById(R.id.id_todo_rv) as RecyclerView?
        adapter = TodoRvAdapter(this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.addItemDecoration(getDecoration())
        recyclerView!!.adapter = adapter
        adapter!!.positionClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            present!!.handleItemClick(it)
        }

        adapter!!.positionLongClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            present!!.handleItemRemove(it)
            showAlertDialog("删除当前提醒", null, { _, _ -> adapter!!.removeData(it.index) }, { _, _ -> })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        present!!.onDestroy()
    }

    override fun getContext(): Context {
        return this
    }

    override fun onItemAdded(data: NormalRemindItemData?) {
        adapter!!.addData(data)
        adapter!!.notifyItemInserted(adapter!!.itemCount)
    }

    override fun onRangeItemAdded(datas: MutableList<NormalRemindItemData>?) {
        adapter!!.addDatas(datas)
        adapter!!.notifyDataSetChanged()
    }
}
