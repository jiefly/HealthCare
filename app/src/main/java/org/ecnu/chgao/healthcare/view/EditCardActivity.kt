package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.EditCardRvAdapter
import org.ecnu.chgao.healthcare.present.EditCardPresent
import rx.android.schedulers.AndroidSchedulers

class EditCardActivity : BaseActivity(), EditCardViewer {
    var recyclerView: RecyclerView? = null
    var adapter: EditCardRvAdapter? = null
    var present: EditCardPresent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)
        useCustomToolbar(title = "管理卡片")
        present = EditCardPresent(this)
        initView()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.id_edit_card_rv) as RecyclerView
        adapter = EditCardRvAdapter(this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(resources.getDrawable(R.drawable.div_bg))
        recyclerView?.addItemDecoration(decoration)
        recyclerView?.adapter = adapter
        adapter!!.addDatas(present!!.allCard)
        adapter!!.notifyDataSetChanged()
        adapter!!.positionClicks.observeOn(AndroidSchedulers.mainThread()).subscribe {
            present!!.toogleEnable(it.getmIndex())
        }
    }

    override fun onPause() {
        super.onPause()
        present!!.updateCardState()
    }

    override fun getContext(): Context {
        return this
    }
}
