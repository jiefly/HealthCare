package org.ecnu.chgao.healthcare.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.LocationSource
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.PolylineOptions
import org.ecnu.chgao.healthcare.R
import org.ecnu.chgao.healthcare.bean.LocationUploadBean
import org.ecnu.chgao.healthcare.present.LocationPresent


class LocationActivity : BaseActivity(), LocationViewer, AMapLocationListener, LocationSource {
    lateinit var present: LocationPresent
    override fun onLocationChanged(p0: AMapLocation?) {
        if (p0 != null) {
            if (p0.errorCode == 0) {
                listener?.onLocationChanged(p0)
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + p0.errorCode + ", errInfo:"
                        + p0.errorInfo)
            }
        }
    }


    override fun getContext(): Context {
        return this
    }

    val datas = ArrayList<LatLng>()
    var locationOption: AMapLocationClientOption? = null
    var locationClient: AMapLocationClient? = null
    var listener: LocationSource.OnLocationChangedListener? = null
    lateinit var mapView: MapView
    var aMap: AMap? = null
    lateinit var uiSettings: UiSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap)
        useCustomToolbar(title = "运动轨迹", onRightIconClick = View.OnClickListener {
            //show history location
        })
        mapView = findViewById(R.id.id_map) as MapView
        mapView.onCreate(savedInstanceState)
        initMap()
        present = LocationPresent(this)
        createLatLng(present.todayLocations)
    }

    private fun createLatLng(locations: List<LocationUploadBean>) {
        val latLngs = ArrayList<LatLng>()
        locations.mapTo(latLngs, { it -> LatLng(it.getmLatitude(), it.getmLongitude()) })
        val colorList = java.util.ArrayList<Int>()
        colorList.add(Color.RED)
        colorList.add(Color.YELLOW)
        colorList.add(Color.GREEN)

        val options = PolylineOptions()
        options.width(10f)
        options.addAll(latLngs)
        options.colorValues(colorList)
        aMap?.addPolyline(options)
    }

    private fun initMap() {
        if (aMap == null) {
            aMap = mapView.map
        }
        uiSettings = aMap!!.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isScaleControlsEnabled = true
        uiSettings.isMyLocationButtonEnabled = true
        aMap!!.isMyLocationEnabled = true


        mapView.map.setLocationSource(this)
        mapView.map.isMyLocationEnabled = true
        locationClient = AMapLocationClient(application)
        locationClient!!.setLocationListener(this)
        locationOption = AMapLocationClientOption()
        locationOption!!.isOnceLocation = true
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
        mapView.onDestroy()
        locationClient?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
}
