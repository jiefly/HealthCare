package org.ecnu.chgao.healthcare.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.ecnu.chgao.healthcare.application.BaseApplication;
import org.ecnu.chgao.healthcare.bean.AccountInfo;
import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.bean.StepUploadBean;
import org.ecnu.chgao.healthcare.bean.UploadPackage;
import org.ecnu.chgao.healthcare.connection.http.HttpMethod;
import org.ecnu.chgao.healthcare.connection.http.NetConnection;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.model.LoginModel;
import org.ecnu.chgao.healthcare.step.bean.StepData;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.ApiStores;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.util.DbUtils;
import org.ecnu.chgao.healthcare.util.NetworkUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static org.ecnu.chgao.healthcare.util.DateUtilKt.getTodayDate;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class PackageUploadService extends IntentService {
    private static final String TAG = "PackageUploadService";

    public PackageUploadService() {
        super("PackageUploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            storeStepUploadBean();
        }
    }

    public void storeStepUploadBean() {
        List<StepData> list = DbUtils.getQueryByWhere(StepData.class, "today", new String[]{getTodayDate()}, StepService.DB_NAME);
        int step = 0;
        if (list.size() == 0 || list.isEmpty()) {
            Log.i(TAG, "未查询到当日运动信息");
        } else if (list.size() == 1) {
            step = Integer.parseInt(list.get(0).getStep());
        } else {
            Log.v(TAG, "出错了！");
        }
        StepUploadBean stepUploadBean = new StepUploadBean(System.currentTimeMillis());
        stepUploadBean.setmTask(7000);
        stepUploadBean.setmStep(step);
        generateAndStoreUploadPackage(stepUploadBean);
        //query packages,if packages number is more than 6,will send data to server
        handlePackage();
    }

    private void handlePackage() {
        NetworkUtil.NetworkType connection = NetworkUtil.getNetworkConnectionType(this);
        if (connection.equals(NetworkUtil.NetworkType.NO_CONNECTION)) {
            return;
        }
        List<UploadPackage> packages = DbUtils.getQueryAll(UploadPackage.class, StepService.DB_NAME);
        if (packages == null || packages.size() == 0) {
            Log.e(TAG, "no upload packages");
            return;
        }

        if (connection.equals(NetworkUtil.NetworkType.CONNNECTION_WIFI)) {
            uploadPackages(packages);
            //plz clear database after upload packages success
            return;
        }
        //upload by mobile network
        if (packages.size() > 10) {
            uploadPackages(packages);
        }
    }

    private void uploadPackages(List<UploadPackage> packages) {
        Log.i(TAG, "uploadPackages,package size:" + packages.size());
        JSONObject object = new JSONObject();
        try {
            object.put(AccountInfo.USER, ((BaseApplication) getApplication()).getSharedPreferencesUtils().getParam(LoginModel.ACCOUNT_KEY, ""));
            object.put(AccountInfo.HEADER, Config.HEADER_STEP_PACKAGE);
            object.put(AccountInfo.ACTION, Config.ACTION_UPLOAD);
            object.put("packages", new Gson().toJson(packages));
            Log.i(TAG, object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new NetConnection(this, ApiStores.API_SERVER_URL, HttpMethod.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                clearDB();
                Log.i(TAG, "upload packages success");
            }

            @Override
            public void onFail(String reason) {
                Log.e(TAG, "upload packages failed");
            }
        }, object.toString());
    }

    private void clearDB() {
        DbUtils.deleteAll(UploadPackage.class, StepService.DB_NAME);
    }

    private void generateAndStoreUploadPackage(StepUploadBean stepUploadBean) {
        List<LocationUploadBean> locations = DbUtils.getQueryByWhere(LocationUploadBean.class, "used", new String[]{"false"}, StepService.DB_NAME);
        //remove locations in db
        for (LocationUploadBean location : locations) {
            location.setmUsed(true);
            DbUtils.update(location, StepService.DB_NAME);
        }
        UploadPackage uploadPackage = new UploadPackage(System.currentTimeMillis());
        uploadPackage.setStep(stepUploadBean, locations);
        DbUtils.insert(uploadPackage, StepService.DB_NAME);
        Log.i(TAG, new Gson().toJson(uploadPackage));
    }
}
