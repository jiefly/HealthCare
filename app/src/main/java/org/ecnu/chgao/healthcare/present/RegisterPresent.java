package org.ecnu.chgao.healthcare.present;

import android.util.Log;

import org.ecnu.chgao.healthcare.bean.UserAction;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.model.RegisterModel;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.view.RegisterViewer;
import org.json.JSONException;

import java.util.Objects;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;

/**
 * Created by chgao on 17-5-26.
 */

public class RegisterPresent extends BasePresent<RegisterViewer, RegisterModel> {
    private static final String TAG = "RegisterPresent";
    private String mPhone;

    public RegisterPresent(RegisterViewer viewer, RegisterModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public RegisterPresent(RegisterViewer viewer) {
        DEBUG = true;
        this.mViewer = viewer;
    }

    public void onGetSmsCodeClick(String phone) {
        mPhone = phone;
        UserAction action = new UserAction(mViewer.getContext());
        try {
            action.getSmsCode(phone, Config.ACTION_GET_SMS_CODE, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.i(TAG, "send sms code success");
                }

                @Override
                public void onFail(String message) {
                    Log.w(TAG, message);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*SMSSDK.getInstance().getSmsCodeAsyn(phone, "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(String s) {

            }

            @Override
            public void getCodeFail(int i, String s) {

            }
        });*/
    }

    public void onRegisterClick(final String phone, final String smsCode, final String pwd) {
        if (DEBUG) {
            mViewer.showProgress("注册中...");
            UserAction ua = new UserAction(mViewer.getContext());
            try {
                ua.register(phone, pwd, smsCode, Config.ACTION_REGISTER, new NetworkCallback() {
                    @Override
                    public void onSuccess(String result) {
                        mViewer.dismissProgress();
                        //if register success,loginSuccess and jump to main activity
                        mViewer.onRegisterSuccess();
                    }

                    @Override
                    public void onFail(String result) {
                        mViewer.dismissProgress();
                        //tell user register failed
                        mViewer.onRegisterFailed(result);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
        //should get sms code before register
        if (mPhone == null) {
            mViewer.showToast("请先获取验证码");
            return;
        }
        //phone can't change during input sms code
        if (!Objects.equals(phone, mPhone)) {
            mViewer.showToast("你获取验证码的手机号与当前手机号不匹配，请检查后再注册");
            return;
        }

        SMSSDK.getInstance().checkSmsCodeAsyn(phone, smsCode,
                new SmscheckListener() {
                    @Override
                    public void checkCodeSuccess(String s) {
                        //if the sms code is correct,will invoke register function
                        UserAction ua = new UserAction(mViewer.getContext());
                        try {
                            ua.register(phone, pwd, smsCode, Config.ACTION_REGISTER, new NetworkCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    mViewer.dismissProgress();
                                    mViewer.onRegisterSuccess();
                                }

                                @Override
                                public void onFail(String result) {
                                    mViewer.dismissProgress();
                                    //tell user register failed
                                    mViewer.onRegisterFailed(result);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void checkCodeFail(int i, String s) {
                        //if sms code is wrong,tell user
                        mViewer.showAlertDialog("验证码错误", "您输入的验证码有误。", null, null);
                    }
                });
    }
}
