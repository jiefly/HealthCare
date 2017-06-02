package org.ecnu.chgao.healthcare.service;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chgao on 17-5-27.
 */

public interface GithubTestService {
    @GET("users/{user}/repos")
    Call<List<JSONObject>> getApi(@Path("user")String user);
}
