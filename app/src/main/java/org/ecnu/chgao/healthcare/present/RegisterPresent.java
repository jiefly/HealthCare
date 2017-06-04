package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.bean.UserAction;
import org.ecnu.chgao.healthcare.model.RegisterModel;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.view.RegisterViewer;
import org.json.JSONException;

import java.util.Objects;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

/**
 * Created by chgao on 17-5-26.
 */

public class RegisterPresent extends BasePresent<RegisterViewer, RegisterModel> {
    private String mPhone;

    public RegisterPresent(RegisterViewer viewer, RegisterModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public RegisterPresent(RegisterViewer viewer) {
        this.mViewer = viewer;
    }

    public void onGetSmsCodeClick(String phone) {
        mPhone = phone;
        SMSSDK.getInstance().getSmsCodeAsyn(phone, "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(String s) {

            }

            @Override
            public void getCodeFail(int i, String s) {

            }
        });
    }

    public void onRegisterClick(final String phone, String smsCode, final String pwd) {
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
                            ua.register(phone, pwd, Config.ACTION_REGISTER, new UserAction.SuccessCallback() {
                                @Override
                                public void onSuccess(String jsonResult) {
                                    //if register success,login and jump to main activity
                                    mViewer.showToast("注册成功");
                                    mViewer.onRegisterSuccess();
                                }
                            }, new UserAction.FailCallback() {

                                @Override
                                public void onFail(int status, int reason) {
                                    //tell user register failed
                                    mViewer.showToast("注册失败");
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
