package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.EditCardRvAdapter
import org.ecnu.chgao.healthcare.bean.NormalSettingItemData
import rx.android.schedulers.AndroidSchedulers

class SettingActivity : BaseActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: EditCardRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        useCustomToolbar(title = "设置")
        initView()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.id_setting_rv) as RecyclerView
        adapter = EditCardRvAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(getDecoration())
        recyclerView.adapter = adapter
        adapter.addData(NormalSettingItemData(0, "仅在wifi下上传运动信息", true))
        adapter.addData(NormalSettingItemData(1, "开启跌到检测", true))
        adapter.notifyDataSetChanged()
        adapter.positionClicks.observeOn(AndroidSchedulers.mainThread()).subscribe {
            showToast("index:${it.getmIndex()} name:${it.name}")
        }
    }

    override fun onPause() {
        super.onPause()
    }
}
