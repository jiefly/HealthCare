package org.ecnu.chgao.healthcare.connection.http;
/**
 * @author MingLei Jia
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class BaseNetConnection {

    private Context context = null;
    private String SHAREDPREFERENCE_SESSION = "SharedPreferences_sessionId";
    private String SESSION_ID = "sessionId";
    private String DEFAULT_SESSION_ID = "-1";

    public BaseNetConnection(Context context) {
        this.context = context;
    }

    public BaseNetConnection(Context context, final String url,
                             final HttpMethod httpMethod, @NonNull final NetworkCallback callback, final int action, final String jsonParams) {

        this.context = context;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                StringBuilder jsonResult = new StringBuilder();
                URLConnection conn = null;

                String sessionId = BaseNetConnection.this.get_cashed_sessionId();

                try {
                    switch (httpMethod) {
                        case POST:

//						StringBuffer postParams = new StringBuffer();
//						postParams.append("jsonRequestParams").append("=")
//								.append(jsonParams);

                            conn = new URL(url).openConnection();
                            conn.setRequestProperty("connection", "Keep-Alive");
                            if (!sessionId
                                    .equals(BaseNetConnection.this.DEFAULT_SESSION_ID) && sessionId != null) {

                                conn.setRequestProperty("Cookie", "JSESSIONID="
                                        + sessionId);
                            }
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            conn.connect();
                            BufferedWriter bw = new BufferedWriter(
                                    new OutputStreamWriter(conn.getOutputStream(),
                                            "UTF-8"));
                            bw.write(jsonParams.toString());
                            bw.flush();
                            bw.close();


                            break;

                        case GET:
                            if (sessionId
                                    .equals(BaseNetConnection.this.DEFAULT_SESSION_ID)) {
                                conn = new URL(url + "?" + "jsonRequestParams"
                                        + "=" + jsonParams).openConnection();//�򿪺�URL֮�������
                            } else {

                                conn = new URL(url + "?" + "jsonRequestParams"
                                        + "=" + jsonParams + ";" + "JSESSIONID="
                                        + sessionId).openConnection();
                            }
                            conn.setRequestProperty("connection", "Keep-Alive");
                            conn.connect();
                            break;

                    }

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(),
                                    "utf-8"));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        jsonResult.append(line);
                    }

                    br.close();

                    return jsonResult.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                if (result != null) {
                    String[] results = result.split("#");
                    if (results.length > 1) {
                        result = results[0];
                        BaseNetConnection.this.cash_sessionId(results[1]);
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if ("success".equals(jsonObject.getString("result"))) {
                            callback.onSuccess(result);
                        } else {
                            callback.onFail(jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callback.onFail("网络连接错误");
                }
            }
        }.execute();

    }

    /**
     * �����������ص�sessionId���ڱ���
     *
     * @param sessionId sessionId
     */
    public void cash_sessionId(String sessionId) {

        SharedPreferences sd = context.getSharedPreferences(
                this.SHAREDPREFERENCE_SESSION, Context.MODE_PRIVATE);
        Editor e = sd.edit();
        e.putString(this.SESSION_ID, sessionId);
        e.commit();

    }

    /**
     * ��ȡ���ڱ��ص�sessionId
     *
     * @return
     */
    private String get_cashed_sessionId() {
        SharedPreferences sd = context.getSharedPreferences(
                this.SHAREDPREFERENCE_SESSION, Context.MODE_PRIVATE);
        return sd.getString(this.SESSION_ID, this.DEFAULT_SESSION_ID);
    }

}
