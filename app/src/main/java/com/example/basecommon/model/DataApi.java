package com.example.basecommon.model;

import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.LoginInfo;
import com.example.basecommon.model.object.SearchCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataApi {
    @POST("GetUserImage")
    Single<String> GetUserImage(@Body SearchCondition searchCondition);

    @POST("GetLoginInfo")
    Single<LoginInfo> GetLoginInfo(@Body SearchCondition searchCondition);
    @POST("CheckAppVersion")
    Single<AppVersion> CheckAppVersion(@Body SearchCondition searchCondition);

    @POST("checkAppProgramsPowerAndLoginHistory")
    Single<List<AppVersion>> checkAppProgramsPowerAndLoginHistory(@Body SearchCondition searchCondition);

    @POST("InsertAppLoginHistory")
    Single<AppVersion> InsertAppLoginHistory(@Body SearchCondition searchCondition);

    @POST("GetNoticeData2")
    Single<AppVersion> GetNoticeData2(@Body SearchCondition searchCondition);

}
