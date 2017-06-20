package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.StepRvAdapter
import org.ecnu.chgao.healthcare.present.StepPresent
import org.ecnu.chgao.healthcare.step.bean.StepData

class StepHistoryActivity : BaseActivity(), StepViewer {

    var recyclerView: RecyclerView? = null
    var adapter: StepRvAdapter? = null
    var chart: LineChart? = null
    var lineDataSet: LineDataSet? = null
    var chartRawData = ArrayList<Entry>()
    var present: StepPresent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_history)
        useCustomToolbar(title = "历史记录", onLeftIconClick = View.OnClickListener { onBackPressed() })
        recyclerView = findViewById(R.id.id_step_rv) as RecyclerView
        chart = findViewById(R.id.id_step_chart) as LineChart
        initRv()
        initChart()
        present = StepPresent(this)
        present!!.fetchData()
    }

    private fun initChart() {
        initLines()
        chart?.description?.text = "步数"
        chart?.setScaleEnabled(true)
        chart?.data = LineData(lineDataSet)
    }

    private fun initLines(): Boolean {
        lineDataSet = LineDataSet(chartRawData, "每天运动量")
        lineDataSet!!.mode = LineDataSet.Mode.CUBIC_BEZIER
        (lineDataSet as LineDataSet).color = Color.BLACK
        lineDataSet!!.setCircleColor(Color.RED)
        lineDataSet!!.lineWidth = 1f//设置线宽
        lineDataSet!!.circleRadius = 1.5f//设置焦点圆心的大小
        lineDataSet!!.enableDashedHighlightLine(10f, 5f, 0f)//点击后的高亮线的显示样式
        lineDataSet!!.highlightLineWidth = 1f//设置点击交点后显示高亮线宽
        lineDataSet!!.isHighlightEnabled = false//是否禁用点击高亮线
        lineDataSet!!.highLightColor = Color.RED//设置点击交点后显示交高亮线的颜色
        lineDataSet!!.valueTextSize = 9f//设置显示值的文字大小
        lineDataSet!!.setDrawFilled(true)//设置禁用范围背景填充
        lineDataSet!!.fillColor = resources.getColor(R.color.yellow)
        return true
    }

    private fun initRv() {
        adapter = StepRvAdapter(this)
        recyclerView?.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView?.addItemDecoration(getDecoration())
        recyclerView?.adapter = adapter
    }

    override fun fillData(datas: MutableList<StepData>?) {
        if (datas!!.size == 0) {
            return
        }
        var index = 1
        datas.asReversed().map {
            //data for recycler view
            adapter!!.addData(it)
            //data for chart
            chart!!.data.dataSets[0].addEntry(Entry(index.toFloat(), it.step.toFloat()))
            index += 1
        }
        adapter!!.notifyDataSetChanged()
        chart!!.data.notifyDataChanged()
        chart!!.notifyDataSetChanged()
    }

    override fun getContext(): Context {
        return this
    }


}
