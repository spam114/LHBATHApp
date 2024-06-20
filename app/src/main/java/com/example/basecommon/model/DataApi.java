package com.example.basecommon.model;

import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.CustomLocation;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.LoginInfo;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.SupervisorWorkType;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataApi {
    // region 어플 로그인
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

    @POST("GetLeaderType")
    Single<Supervisor> GetLeaderType(@Body String TelNumber);
    // endregion

    // region 장소
    @POST("GetCustomLocationNo")
    Single<List<CustomLocation>> GetCustomLocationNo();

    @POST("GetSearchLocationData")
    Single<List<Contract>> GetSearchLocationData(@Body int LocationNo);
    @POST("GetSearchCustomLocationNo")
    Single<List<CustomLocation>> GetSearchCustomLocationNo(@Body String SearchWord);
    // endregion

    // region 서류 관련
    // Read
    @POST("GetSupervisorWorder")
    Single<List<SupervisorWorder>> GetSupervisorWorder(@Body int LocationNo);
    @POST("GetSupervisorASItemStandardParent")
    Single<List<SupervisorASItemStandard>> GetSupervisorASItemStandardParent();
    @POST("GetWorkTypeByBusinessClassCode")
    Single<List<SupervisorWorkType>> GetWorkTypeByBusinessClassCode(@Body int BusinessClassCode);
    @POST("GetSupervisorWoImageData")
    Single<List<SupervisorWoImage>> GetSupervisorWoImageData(@Body String Key);
    //Insert
    @POST("SetSupervisorWorderData")
    Single<SupervisorWorder> SetSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    @POST("InsertSupervisorWoImageMulti")
    Single<List<SupervisorWoImage>> InsertSupervisorWoImageMulti(@Body List<SupervisorWoImage> Images);

    //Update
    @POST("UpdateSupervisorWorderData")
    Single<SupervisorWorder> UpdateSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    //Delete
    @POST("DeleteSupervisorWorderData")
    Single<SupervisorWorder> DeleteSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    @POST("DeleteSupervisorWoImage")
    Single<List<SupervisorWoImage>> DeleteSupervisorWoImage(@Body SupervisorWoImage supervisorWoImage);

    // endregion
}
