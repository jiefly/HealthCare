package org.ecnu.chgao.healthcare.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.MyLocationStyle
import org.ecnu.chgao.healthcare.R


class Amap : AppCompatActivity(), LocationSource, AMapLocationListener {
    override fun onLocationChanged(p0: AMapLocation?) {
        if (listener != null && p0 != null) {
            if (p0.errorCode == 0) {
                listener!!.onLocationChanged(p0)
            }
        }
    }

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
