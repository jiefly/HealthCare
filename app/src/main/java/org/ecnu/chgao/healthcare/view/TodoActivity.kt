package org.ecnu.chgao.healthcare.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.getbase.floatingactionbutton.FloatingActionButton
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.TodoRvAdapter
import org.ecnu.chgao.healthcare.bean.MedicalNotificationEditItemData
import org.ecnu.chgao.healthcare.bean.NormalRemindItemData
import org.ecnu.chgao.healthcare.present.ToDoPresent
import rx.android.schedulers.AndroidSchedulers

class TodoActivity : BaseActivity(), ToDoViewer {
    companion object {
        @JvmField
        val REQUEST_ADD = 0x0101
        val REQUEST_CHANGE = 0x1010
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoRvAdapter
    private lateinit var present: ToDoPresent
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
            startActivityForResult(Intent(this, NotificationEditActivity::class.java), REQUEST_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val bundle = data!!.getBundleExtra("bundle")
            val result = ArrayList<MedicalNotificationEditItemData>()
            MedicalNotificationEditItemData.Type.values().mapTo(result) { bundle.getParcelable(it.getName()) }

            when (requestCode) {
                REQUEST_ADD -> {
                    present.addItem(present.generateRemindData(result))
                }
                REQUEST_CHANGE -> {
                    present.changeItem(present.generateRemindData(result))
                }
            }

        }
    }

    override fun onItemChanged(index: Int, data: NormalRemindItemData?) {
        adapter.datas[index] = data
        adapter.notifyItemChanged(index)
    }

    private fun initRv() {
        recyclerView = (findViewById(R.id.id_todo_rv) as RecyclerView?)!!
        adapter = TodoRvAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(getDecoration())
        recyclerView.adapter = adapter
        adapter.positionClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            present.handleItemClick(it)
            val intent = Intent(this, NotificationEditActivity::class.java)
            startActivityForResult(intent, REQUEST_CHANGE)
        }

        adapter.positionLongClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            present.handleItemRemove(it)
            showAlertDialog("删除当前提醒", null, { _, _ -> adapter.removeData(it.index) }, { _, _ -> })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        present.onDestroy()
    }

    override fun getContext(): Context {
        return this
    }

    override fun onItemAdded(data: NormalRemindItemData?) {
        if (data!!.index == -1) {
            data.index = adapter.itemCount
        }
        adapter.addData(data)
        adapter.notifyItemInserted(adapter.itemCount)
    }

    override fun onRangeItemAdded(datas: MutableList<NormalRemindItemData>?) {
        adapter.addDatas(datas)
        adapter.notifyDataSetChanged()
    }
}
