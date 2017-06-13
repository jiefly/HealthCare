package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bigkoo.pickerview.OptionsPickerView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.NotificationEditRvAdapter
import org.ecnu.chgao.healthcare.model.MedicalNotificationEditItemData
import rx.android.schedulers.AndroidSchedulers


class NotificationEditActivity : BaseActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: NotificationEditRvAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_edit)
        useCustomToolbar(title = "添加提醒", onRightIconClick = View.OnClickListener { showToast("OK") })
        initRv()
//        findViewById(R.id.id_notification_edit_test).setOnClickListener {
        val pvOptions = OptionsPickerView.Builder(this, OptionsPickerView.OnOptionsSelectListener { options1, options2, options3, v ->

        }).setLabels("", "时", "分")
                .isCenterLabel(true)
                .build()
        val result = initPickerData()
        pvOptions.setNPicker(result[0], result[1], result[2])
        pvOptions.show()
//        }
    }

    private fun initRv() {
        recyclerView = findViewById(R.id.id_edit_notification_rv) as RecyclerView?
        adapter = NotificationEditRvAdapter(this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.addItemDecoration(getDecoration())
        recyclerView!!.adapter = adapter
        adapter!!.positionClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it is MedicalNotificationEditItemData) {
                when (it.getmType()) {
                    MedicalNotificationEditItemData.Type.MEDCIAL_NAME -> {
                        showInputAlertDialog(it.name, object : OnInputAlert {
                            override fun onInputDismiss(value: String) {
                                adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.MEDCIAL_NAME).setValue(value))
                                adapter!!.notifyDataSetChanged()
                            }
                        })
                    }
                    MedicalNotificationEditItemData.Type.DOURATION -> {
                    }
                    MedicalNotificationEditItemData.Type.PILL_PERTIME -> {
                    }
                    MedicalNotificationEditItemData.Type.TIMES_PERDAY -> {
                    }
                    MedicalNotificationEditItemData.Type.START_TIME -> {
                    }
                    MedicalNotificationEditItemData.Type.INTEVAL -> {
                    }
                    MedicalNotificationEditItemData.Type.REMIND_WAY -> {
                    }
                    else -> {
                        Log.w(this.javaClass.simpleName, "wrong MedicalNotificationEditItemData type")
                    }
                }
            }
        }
        adapter!!.positionLongClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
        }
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.MEDCIAL_NAME).setValue("阿姆西林"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.DOURATION).setValue("14 天"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.PILL_PERTIME).setValue("2 片"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.TIMES_PERDAY).setValue("3 次"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.START_TIME).setValue("08:00"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.INTEVAL).setValue("6 小时"))
        adapter!!.addData(MedicalNotificationEditItemData().setmType(MedicalNotificationEditItemData.Type.REMIND_WAY).setValue("响铃&震动"))
        adapter!!.notifyDataSetChanged()
    }

    fun initPickerData(): List<List<Any>> {
        val result = ArrayList<List<Any>>()
        //1
        val option1 = ArrayList<String>()
        option1.add("只响一次")
        option1.add("每天")
        option1.add("星期天")
        option1.add("星期一")
        option1.add("星期二")
        option1.add("星期三")
        option1.add("星期四")
        option1.add("星期五")
        option1.add("星期六")
        //2
        val option2 = ArrayList<String>()
        (0..23).mapTo(option2) {
            if (it < 9) {
                "0$it"
            } else {
                "$it"
            }
        }
        //3
        val option3 = ArrayList<String>()
        (0..59).mapTo(option3) {
            if (it < 9) {
                "0$it"
            } else {
                "$it"
            }
        }

        result.add(option1)
        result.add(option2)
        result.add(option3)
        return result
    }
}
