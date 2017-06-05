package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import org.ecnu.chgao.healthcare.bean.StepHistoryData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.adapter.StepRvAdapter
import java.util.*
import kotlin.collections.ArrayList

class StepHistoryActivity : BaseActivity(), StepViewer {
    override fun getContext(): Context {
        return this
    }

    var recyclerView: RecyclerView? = null
    var adapter: StepRvAdapter? = null
    var chart: LineChart? = null
    var lineDataSet: LineDataSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_history)
        useCustomToolbar(title = "步数", onLeftIconClick = View.OnClickListener { onBackPressed() })
        recyclerView = findViewById(R.id.id_step_rv) as RecyclerView
        chart = findViewById(R.id.id_step_chart) as LineChart
        initRv()
        initChart()
    }

    private fun initChart() {
        chart?.description?.text = "步数"
        mockData()
        chart?.setScaleEnabled(true)
        chart?.data = LineData(lineDataSet)
        chart?.data?.notifyDataChanged()
        chart?.notifyDataSetChanged()
    }

    private fun mockData() {
        var data = ArrayList<Entry>()
        (0..10).mapTo(data) { Entry(it.toFloat(),Random().nextInt(100).toFloat()) }
        lineDataSet = LineDataSet(data,"")
        lineDataSet!!.mode = LineDataSet.Mode.CUBIC_BEZIER
        (lineDataSet as LineDataSet).color = Color.BLACK;
        lineDataSet!!.setCircleColor(Color.RED);
        lineDataSet!!.lineWidth = 1f;//设置线宽
        lineDataSet!!.circleRadius = 1.5f;//设置焦点圆心的大小
        lineDataSet!!.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        lineDataSet!!.highlightLineWidth = 1f;//设置点击交点后显示高亮线宽
        lineDataSet!!.isHighlightEnabled = false;//是否禁用点击高亮线
        lineDataSet!!.highLightColor = Color.RED;//设置点击交点后显示交高亮线的颜色
        lineDataSet!!.valueTextSize = 9f;//设置显示值的文字大小
        lineDataSet!!.setDrawFilled(true);//设置禁用范围背景填充
        lineDataSet!!.fillColor = Color.GREEN
    }

    private fun initRv() {
        adapter = StepRvAdapter(this)
        recyclerView?.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(resources.getDrawable(R.drawable.div_bg))
        recyclerView?.addItemDecoration(decoration)
        recyclerView?.adapter = adapter
        for (i in 1..10)
            adapter?.addData(StepHistoryData().setDate(Date()).setValue(100 + i))
        adapter?.notifyDataSetChanged()
    }
}
