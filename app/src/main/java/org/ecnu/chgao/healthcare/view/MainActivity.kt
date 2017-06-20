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
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import org.ecnu.chgao.healthcare.Manifest
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.MainRvAdapter
import org.ecnu.chgao.healthcare.bean.MainMenuClickEvent
import org.ecnu.chgao.healthcare.bean.NormalMainItemData
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.service.FallDetectService
import org.ecnu.chgao.healthcare.step.service.StepService
import rx.android.schedulers.AndroidSchedulers

class MainActivity : BaseActivity(), MainViewer, NavigationView.OnNavigationItemSelectedListener {
    val permissionR = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val permissionW = android.Manifest.permission.READ_EXTERNAL_STORAGE
    val permissionL = android.Manifest.permission.ACCESS_FINE_LOCATION
    val permissionC = android.Manifest.permission.CALL_PHONE
    var mainPresent: MainPresent? = null
    var recyclerView: RecyclerView? = null
    var adapter: MainRvAdapter? = null
    var drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
    private lateinit var locationClient: AMapLocationClient
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
        stopLocation()

    }

    private fun initView() {
        initDrawer()
        initRv()
    }

    private fun initDrawer() {
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.findViewById(R.id.id_main_logout).setOnClickListener {
            mainPresent!!.logout()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer!!.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_account -> navigate<UserInfoActivity>()
            R.id.nav_reset_pwd -> navigate<FindPwdActivity>()
            R.id.nav_setting -> navigate<SettingActivity>()
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
        checkPermission(CheckPermListener { }, R.string.ask_again, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION)
        adapter!!.replaceAllDatas(mainPresent!!.allCards)
        mainPresent!!.updateUi()
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
                NormalMainItemData.ItemType.NOTIFICATION -> navigate<TodoActivity>()
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

    override fun jumpToLogin() {
        navigate<LoginActivity>()
        finish()
    }

    override fun startLocation(option: AMapLocationClientOption, locationListener: AMapLocationListener) {
        locationClient = AMapLocationClient(this.applicationContext)
        locationClient.setLocationOption(option)
        locationClient.setLocationListener(locationListener)
        locationClient.startLocation()
    }

    override fun stopLocation() {
        locationClient.let {
            locationClient.stopLocation()
            locationClient.onDestroy()
        }
    }


    override fun getContext(): Context {
        return this
    }
}
