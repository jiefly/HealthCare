package org.ecnu.chgao.healthcare.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.getbase.floatingactionbutton.FloatingActionsMenu
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.service.FallDetectService
import org.ecnu.chgao.healthcare.step.service.StepService
import org.ecnu.chgao.healthcare.view.customview.StepArcView

class MainActivity : BaseActivity(), MainViewer {
    var mainPresent: MainPresent? = null
    var cover: View? = null
    var menu: FloatingActionsMenu? = null
    var stepArc: StepArcView? = null
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
        stepArc!!.setCurrentCount(totalCount, currentCount)
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
        stepArc = findViewById(R.id.id_main_step_arc_view) as StepArcView
        stepArc!!.setCurrentCount(mainPresent!!.taskStep, mainPresent!!.currentStep)
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
