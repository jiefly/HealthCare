package org.ecnu.chgao.healthcare.present;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.ecnu.chgao.healthcare.alarmmanager.AlarmManagerUtil;
import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.bean.NormalMainItemData;
import org.ecnu.chgao.healthcare.bean.StepUploadBean;
import org.ecnu.chgao.healthcare.model.EditCardModel;
import org.ecnu.chgao.healthcare.model.MainModel;
import org.ecnu.chgao.healthcare.step.UpdateUiCallBack;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.view.MainViewer;

import java.util.List;

/**
 * Created by chgao on 17-5-29.
 */

public class MainPresent extends BasePresent<MainViewer, MainModel> implements UpdateUiCallBack, AMapLocationListener {
    public MainPresent(MainViewer viewer, MainModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public MainPresent(MainViewer viewer) {
        this.mViewer = viewer;
        mModel = new MainModel(mViewer.getContext().getApplicationContext());
        mViewer.startLocation(buildLocationOption(), this);
        AlarmManagerUtil.setAlarmByStoreUploadPackage(mViewer.getContext().getApplicationContext(), 19930918, StepUploadBean.FREQUENCY);
    }

    public void logout() {
        mModel.clearPwd();
        mViewer.jumpToLogin();
    }

    public void onStepServiceConnected(StepService service) {
        service.registerCallback(this);
        mViewer.setStepCount(mModel.getTodayTask(), service.getStepCount());
    }

    public void onStepServiceDisconnected() {
    }

    public void updateTask(int step) {
        mModel.updateTodayTask(step);
    }

    public int getTaskStep() {
        return mModel.getTodayTask();
    }

    public void updateUi() {
        updateUi(mModel.getCurrentStep());
    }

    @Override
    public void updateUi(int stepCount) {
        //updateCurrentStep(stepCount);
        mViewer.setStepCount(mModel.getTodayTask(), stepCount);
    }

    public List<NormalMainItemData> getAllCards() {
        return mModel.getAllCards();
    }

    public void disableCard(NormalMainItemData.ItemType type) {
        mModel.disableCard(EditCardModel.CardType.values()[type.ordinal() - 1]);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                handleLocation(aMapLocation);
                /*StringBuilder sb = new StringBuilder();
                sb.append("结果来源：").append(aMapLocation.getLocationType());
                sb.append("纬度：").append(aMapLocation.getLatitude());
                sb.append("经度:").append(aMapLocation.getLongitude());
                sb.append("精度信息:").append(aMapLocation.getAccuracy());
                sb.append("地址:").append(aMapLocation.getAddress());
                sb.append("国家信息:").append(aMapLocation.getCountry());
                sb.append("省信息:").append(aMapLocation.getProvince());
                sb.append("城市信息:").append(aMapLocation.getCity());
                sb.append("城区信息:").append(aMapLocation.getDistrict());
                sb.append("街道信息:").append(aMapLocation.getStreet());
                sb.append("街道门牌号信息:").append(aMapLocation.getStreetNum());
                sb.append("城市编码:").append(aMapLocation.getCityCode());
                sb.append("地区编码:").append(aMapLocation.getAdCode());
                sb.append("AOI信息:").append(aMapLocation.getAoiName());
                sb.append("建筑物Id:").append(aMapLocation.getBuildingId());
                sb.append("楼层:").append(aMapLocation.getFloor());
                sb.append("定位时间:").append(new Date(aMapLocation.getTime()).toGMTString());
                Log.i("info", sb.toString());*/
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void handleLocation(AMapLocation aMapLocation) {
        LocationUploadBean uploadBean = new LocationUploadBean(System.currentTimeMillis());
        uploadBean.setmAccuracy(aMapLocation.getAccuracy());
        uploadBean.setmAddress(aMapLocation.getAddress());
        uploadBean.setmLatitude(aMapLocation.getLatitude());
        uploadBean.setmLongitude(aMapLocation.getLongitude());
        mModel.storeLocation(uploadBean);
    }


    private AMapLocationClientOption buildLocationOption() {
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
////        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
//        //获取一次定位结果：
//        //该方法默认为false。
//        mLocationOption.setOnceLocation(false);
//        //获取最近3s内精度最高的一次定位结果：
//        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);
//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(LocationUploadBean.FREQUENCY);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
//        //设置是否允许模拟位置,默认为true，允许模拟位置
//        mLocationOption.setMockEnable(true);
//        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
//        mLocationOption.setHttpTimeOut(20000);
//        //关闭缓存机制
//        mLocationOption.setLocationCacheEnable(false);
        return mLocationOption;
    }

}
