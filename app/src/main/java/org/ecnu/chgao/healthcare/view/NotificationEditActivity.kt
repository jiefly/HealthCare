package org.ecnu.chgao.healthcare.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.TimePickerView
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.NotificationEditRvAdapter
import org.ecnu.chgao.healthcare.bean.MedicalNotificationEditItemData
import org.ecnu.chgao.healthcare.bean.RemindData
import org.ecnu.chgao.healthcare.util.dateDetail
import org.ecnu.chgao.healthcare.view.TodoActivity.Companion.REQUEST_CHANGE
import rx.android.schedulers.AndroidSchedulers


class NotificationEditActivity : BaseActivity(), NotificationEditViewer {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationEditRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_edit)
        val action = intent.extras.getInt("action")
        useCustomToolbar(title = "添加提醒", onRightIconClick = View.OnClickListener {
            val intent = Intent()
            val bundle = Bundle()
            adapter.allDatas
                    .filterIsInstance<MedicalNotificationEditItemData>()
                    .forEach { bundle.putParcelable(it.getmType().getName(), it) }
            intent.putExtra("bundle", bundle)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
        if (action == REQUEST_CHANGE) {
            val remind = intent.extras.getSerializable("old") as RemindData
            initRv(remind.getmMedicalNotificationData())
        } else {
            initRv(null)
        }
    }

    private fun initRv(datas: List<MedicalNotificationEditItemData>?) {
        recyclerView = findViewById(R.id.id_edit_notification_rv) as RecyclerView
        adapter = NotificationEditRvAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(getDecoration())
        recyclerView.adapter = adapter
        adapter.positionClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it is MedicalNotificationEditItemData) {
                when (it.getmType()) {
                    MedicalNotificationEditItemData.Type.MEDCIAL_NAME -> {
                        showInputAlertDialog(it.name, object : OnInputAlert {
                            override fun onInputDismiss(value: String) {
                                adapter.onItemChange(it.index, value)
                            }
                        })
                    }
                    MedicalNotificationEditItemData.Type.DOURATION -> {
                        val values = ArrayList<String>()
                        (1..30).mapTo(values) { "$it 天" }
                        generateOptionPicker(it.name, values, OptionsPickerView.OnOptionsSelectListener { options1, _, _, _ ->
                            adapter.onItemChange(it.index, values[options1.toString().toInt()].removeSuffix(" 天"))
                        }).show()
                    }
                    MedicalNotificationEditItemData.Type.PILL_PERTIME -> {
                        val values = ArrayList<String>()
                        (1..20).mapTo(values) { "$it 片" }
                        generateOptionPicker(it.name, values, OptionsPickerView.OnOptionsSelectListener { options1, _, _, _ ->
                            adapter.onItemChange(it.index, values[options1.toString().toInt()].removeSuffix(" 片"))
                        }).show()
                    }
                    MedicalNotificationEditItemData.Type.TIMES_PERDAY -> {
                        val values = ArrayList<String>()
                        (1..10).mapTo(values) { "$it 次" }
                        generateOptionPicker(it.name, values, OptionsPickerView.OnOptionsSelectListener { options1, _, _, _ ->
                            adapter.onItemChange(it.index, values[options1.toString().toInt()].removeSuffix(" 次"))
                        }).show()
                    }
                    MedicalNotificationEditItemData.Type.START_TIME -> {
                        val pvOptions = TimePickerView.Builder(this, TimePickerView.OnTimeSelectListener { date, v ->
                            adapter.onItemChange(it.index, dateDetail(date, false, false, false, true, true, false))
                        }).setType(booleanArrayOf(false, false, false, true, true, false))
                                .isCenterLabel(true)
                                .build()
                        pvOptions.show()
                    }
                    MedicalNotificationEditItemData.Type.INTEVAL -> {
                        val values = ArrayList<String>()
                        (1..24).mapTo(values) { "${it * 0.5} 小时" }
                        generateOptionPicker(it.name, values, OptionsPickerView.OnOptionsSelectListener { options1, _, _, _ ->
                            adapter.onItemChange(it.index, values[options1.toString().toInt()].removeSuffix(" 小时"))
                        }).show()
                    }
                    MedicalNotificationEditItemData.Type.REMIND_WAY -> {
                        val values = Array(3, init = { "" })
                        values[0] = "震动"
                        values[1] = "铃声"
                        values[2] = "震动&铃声"
                        generateOptionPicker(it.name, values.toList(), OptionsPickerView.OnOptionsSelectListener { options1, _, _, _ ->
                            adapter.onItemChange(it.index, values[options1.toString().toInt()])
                        }).show()

                    }
                    else -> {
                        Log.w(this.javaClass.simpleName, "wrong MedicalNotificationEditItemData type")
                    }
                }
            }
        }
        adapter.positionLongClicks.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
        }
        if (datas == null || datas.isEmpty()) {
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.MEDCIAL_NAME).setValue("阿姆西林"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.DOURATION).setValue("14"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.PILL_PERTIME).setValue("2"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.TIMES_PERDAY).setValue("3"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.START_TIME).setValue("08:00"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.INTEVAL).setValue("6.0"))
            adapter.addData(MedicalNotificationEditItemData(MedicalNotificationEditItemData.Type.REMIND_WAY).setValue("响铃"))
        } else {
            adapter.addDatas(datas.sortedBy { it.getmType().value })
        }
        adapter.notifyDataSetChanged()
    }

    fun generateOptionPicker(title: String, datas: List<String>, onOptionsSelectListener: OptionsPickerView.OnOptionsSelectListener): OptionsPickerView<Any> {
        val pvOptions = OptionsPickerView.Builder(this, onOptionsSelectListener)
                .setTitleText(title)
                .build()
        pvOptions.setPicker(datas)
        return pvOptions
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun getContext(): Context {
        return this
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
