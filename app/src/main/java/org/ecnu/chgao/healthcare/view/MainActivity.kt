package org.ecnu.chgao.healthcare.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuItem
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.MainRvAdapter
import org.ecnu.chgao.healthcare.bean.NormalMainItemData
import org.ecnu.chgao.healthcare.model.MainMenuClickEvent
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.service.FallDetectService
import org.ecnu.chgao.healthcare.step.service.StepService
import rx.android.schedulers.AndroidSchedulers

class MainActivity : BaseActivity(), MainViewer, NavigationView.OnNavigationItemSelectedListener {
    var mainPresent: MainPresent? = null
    var recyclerView: RecyclerView? = null
    var adapter: MainRvAdapter? = null
    var drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
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
        setContentView(R.layout.activity_drawer_main)
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
        initDrawer()
        initRv()
    }

    private fun initDrawer() {
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer!!.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_account -> navigate<UserInfoActivity>()
            R.id.nav_reset_pwd -> navigate<FindPwdActivity>()
            else -> showToast("menu item clicked ,item name:${item.title}")
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter!!.replaceAllDatas(mainPresent!!.allCards)
    }

    private fun initRv() {
        recyclerView = findViewById(R.id.id_main_rv) as RecyclerView?
        recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = MainRvAdapter(this)
        recyclerView!!.adapter = adapter
        adapter!!.addDatas(mainPresent!!.allCards)
        adapter!!.notifyDataSetChanged()
        adapter!!.positionClicks.observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it.getmItemType()) {
                NormalMainItemData.ItemType.STEP -> {
                    if (it is MainMenuClickEvent) {
                        drawer!!.openDrawer(Gravity.START)
                    } else {
                        navigate<StepHistoryActivity>()
                    }
                }
                NormalMainItemData.ItemType.LOCATION -> navigate<Amap>()
                NormalMainItemData.ItemType.FOTTER -> navigate<EditCardActivity>()
                else -> showToast("index:${it.getmIndex()} clicked")
            }
        }

        adapter!!.positionLongClicks.observeOn(AndroidSchedulers.mainThread()).subscribe {
            //adapter!!.removeData(it.getmIndex())
            showAlertDialog("删除卡片", "", { _, _ ->
                adapter!!.removeData(it.getmIndex())
                mainPresent!!.disableCard(it.getmItemType())
            }, { _, _ -> })
        }
    }

    override fun getContext(): Context {
        return this
    }
}
