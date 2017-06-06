package org.ecnu.chgao.healthcare.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.getbase.floatingactionbutton.FloatingActionsMenu
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.MainRvAdapter
import org.ecnu.chgao.healthcare.model.NormalMainItemData
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.service.FallDetectService
import org.ecnu.chgao.healthcare.step.service.StepService
import rx.android.schedulers.AndroidSchedulers

class MainActivity : BaseActivity(), MainViewer {
    var mainPresent: MainPresent? = null
    var cover: View? = null
    var menu: FloatingActionsMenu? = null
    var recyclerView: RecyclerView? = null
    var adapter: MainRvAdapter? = null
    val serviceConnection = object : ServiceConnection {


        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val stepService = (service as StepService.StepBinder).service
            mainPresent!!.onStepServiceConnected(stepService)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mainPresent!!.onStepServiceDisconnected()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresent = MainPresent(this)
        initView()
        startService(Intent(this, FallDetectService::class.java))
        bindStepService()
    }

    override fun setStepCount(totalCount: Int, currentCount: Int) {
        adapter!!.setStep(totalCount, currentCount)
    }

    override fun bindStepService() {
        intent = Intent(this, StepService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun initView() {
        initRv()
        cover = findViewById(R.id.id_main_cover)
        menu = findViewById(R.id.id_main_menu) as FloatingActionsMenu
        menu!!.setOnFloatingActionsMenuUpdateListener(object : FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                toggleCover(true)
            }

            override fun onMenuCollapsed() {
                toggleCover(false)
            }
        })
        menu!!.findViewById(R.id.id_main_menu_item_1).setOnClickListener({
            navigate<StepHistoryActivity>()
            menu!!.collapse()
        })
        menu!!.findViewById(R.id.id_main_menu_item_2).setOnClickListener {
            menu!!.collapse()
        }
        menu!!.findViewById(R.id.id_main_menu_item_3).setOnClickListener({
            menu!!.collapse()
        })
    }

    private fun initRv() {
        recyclerView = findViewById(R.id.id_main_rv) as RecyclerView?
        recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = MainRvAdapter(this)
        recyclerView!!.adapter = adapter
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.HEADER))
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FALL_DOWN).setmItemTitle("跌倒检测").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("跌到检测服务正在后台运行。点击可配置相关信息"))
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.NOTIFICATION).setmItemTitle("每日提醒").setmIconRes(R.drawable.ic_snooze_white_24dp).setmContent("每一个用户设置的提醒都应该显示为一条Item"))
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.EVETY_DAY_HEALTH).setmItemTitle("健康小提示").setmIconRes(R.drawable.ic_receipt_blue_50_24dp).setmContent("这里的消息可以来自于服务端的推送。"))
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.LOCATION).setmItemTitle("运动轨迹").setmIconRes(R.drawable.ic_place_white_24dp).setmContent("点击查看运动轨迹"))
        adapter!!.addData(NormalMainItemData().setmItemType(NormalMainItemData.ItemType.FOTTER))
        adapter!!.notifyDataSetChanged()
        adapter!!.positionClicks.observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it.getmItemType()) {
                NormalMainItemData.ItemType.HEADER -> navigate<StepHistoryActivity>()
                NormalMainItemData.ItemType.LOCATION -> navigate<Amap>()
                else -> showToast("index:${it.getmIndex()} clicked")
            }
        }
    }

    private fun toggleCover(show: Boolean) {
        if (cover == null) {
            cover = findViewById(R.id.id_main_cover)
        }
        if (show) {
            cover!!.visibility = View.VISIBLE
            cover!!.alpha = 0.7f
        } else {
            cover!!.visibility = View.GONE
        }
    }

    override fun getContext(): Context {
        return this
    }
}
