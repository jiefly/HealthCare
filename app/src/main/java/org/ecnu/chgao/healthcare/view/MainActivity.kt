package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.getbase.floatingactionbutton.FloatingActionsMenu
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.present.MainPresent
import org.ecnu.chgao.healthcare.service.FallDeteachService

class MainActivity : BaseActivity(), MainViewer {
    override fun getContext(): Context {
        return this
    }

    var mainPresent: MainPresent? = null
    var cover: View? = null
    var menu: FloatingActionsMenu? = null

    init {
        mainPresent = MainPresent(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        startService(Intent(this, FallDeteachService::class.java))
    }

    private fun initView() {
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
}
