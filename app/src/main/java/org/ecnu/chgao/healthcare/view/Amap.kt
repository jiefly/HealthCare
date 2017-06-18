package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.LatLng
import org.ecnu.chgao.healthcare.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Amap : AppCompatActivity(), LocationSource, AMapLocationListener {
    override fun onLocationChanged(p0: AMapLocation?) {
        if (p0 != null) {
            if (p0.errorCode == 0) {
                listener?.onLocationChanged(p0)
                val sb = StringBuilder()
                val amapLocation = p0
                drawLine(LatLng(p0.latitude, p0.longitude))
                amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                sb.append("结果来源：").append(amapLocation.locationType)
                amapLocation.latitude//获取纬度
                sb.append("纬度：").append(amapLocation.latitude)
                amapLocation.longitude//获取经度
                sb.append("经度:").append(amapLocation.longitude)
                amapLocation.accuracy//获取精度信息
                sb.append("精度信息:").append(amapLocation.accuracy)
                amapLocation.address//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                sb.append("地址:").append(amapLocation.address)
                amapLocation.country//国家信息
                sb.append("国家信息:").append(amapLocation.country)
                amapLocation.province//省信息
                sb.append("省信息:").append(amapLocation.province)
                amapLocation.city//城市信息
                sb.append("城市信息:").append(amapLocation.city)
                amapLocation.district//城区信息
                sb.append("城区信息:").append(amapLocation.district)
                amapLocation.street//街道信息
                sb.append("街道信息:").append(amapLocation.street)
                amapLocation.streetNum//街道门牌号信息
                sb.append("街道门牌号信息:").append(amapLocation.streetNum)
                amapLocation.cityCode//城市编码
                sb.append("城市编码:").append(amapLocation.cityCode)
                amapLocation.adCode//地区编码
                sb.append("地区编码:").append(amapLocation.adCode)
                amapLocation.aoiName//获取当前定位点的AOI信息
                sb.append("AOI信息:").append(amapLocation.aoiName)
                amapLocation.buildingId//获取当前室内定位的建筑物Id
                sb.append("建筑物Id:").append(amapLocation.buildingId)
                amapLocation.floor//获取当前室内定位的楼层
                sb.append("楼层:").append(amapLocation.floor)
                // amapLocation.getGpsStatus()//获取GPS的当前状态
//获取定位时间
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date(amapLocation.time)
                df.format(date)
                sb.append("定位时间:").append(df.toString())
                Log.i("info", sb.toString())
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + p0.errorCode + ", errInfo:"
                        + p0.errorInfo)
            }
        }
        if (listener != null && p0 != null) {
            if (p0.errorCode == 0) {
                listener!!.onLocationChanged(p0)
            }
        }
    }

    private fun drawLine(latLng: LatLng) {
        datas.add(latLng)


    }

    val datas = ArrayList<LatLng>()
    var locationOption: AMapLocationClientOption? = null
    var locationClient: AMapLocationClient? = null
    var listener: LocationSource.OnLocationChangedListener? = null
    var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)
        mapView = findViewById(R.id.id_map) as MapView
        mapView!!.onCreate(savedInstanceState)
        //mapView.map.isTrafficEnabled = true
//        val myLocationStyle: MyLocationStyle = MyLocationStyle()


        //初始化定位蓝点样式类
//        mapView!!.map.setMyLocationStyle(myLocationStyle)
        mapView!!.map.setLocationSource(this)
        mapView!!.map.isMyLocationEnabled = true
        locationClient = AMapLocationClient(application)
        locationClient!!.setLocationListener(this)
        /*.setLocationListener {
            if (it != null) {
                if (it.errorCode == 0) {
                    listener?.onLocationChanged(it)
//                    val amapLocation = it
//                    amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    amapLocation.latitude//获取纬度
//                    amapLocation.longitude//获取经度
//                    amapLocation.accuracy//获取精度信息
//                    amapLocation.address//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    amapLocation.country//国家信息
//                    amapLocation.province//省信息
//                    amapLocation.city//城市信息
//                    amapLocation.district//城区信息
//                    amapLocation.street//街道信息
//                    amapLocation.streetNum//街道门牌号信息
//                    amapLocation.cityCode//城市编码
//                    amapLocation.adCode//地区编码
//                    amapLocation.aoiName//获取当前定位点的AOI信息
//                    amapLocation.buildingId//获取当前室内定位的建筑物Id
//                    amapLocation.floor//获取当前室内定位的楼层
//                    // amapLocation.getGpsStatus()//获取GPS的当前状态
////获取定位时间
//                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                    val date = Date(amapLocation.time)
//                    df.format(date)
                } else {
                    Log.e("AmapError", "location Error, ErrCode:"
                            + it.errorCode + ", errInfo:"
                            + it.errorInfo)
                }
            }
        }*/

        locationOption = AMapLocationClientOption()
        locationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        locationClient!!.setLocationOption(locationOption)
        locationClient!!.startLocation()
    }

    override fun deactivate() {
        listener = null
        if (locationClient != null) {
            (locationClient as AMapLocationClient).stopLocation()
            locationClient!!.onDestroy()

        }
        locationClient = null
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        listener = p0
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        locationClient?.onDestroy()
    }
}
