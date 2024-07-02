package com.example.basecommon.model;

import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.CustomLocation;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.LoginInfo;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.SupervisorComplaintImage;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.SupervisorWorkType;
import com.example.basecommon.model.object.WorkListSearch;
import com.example.basecommon.model.object.WorkingList;

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
    
    // 본인의 작업들 가져오기
    @POST("GetWorkingList")
    Single<List<WorkingList>> GetWorkingList(@Body WorkListSearch workListSearch);
    // endregion

    // region 서류 관련
    // Read
    @POST("GetSupervisorWorder")
    Single<List<SupervisorWorder>> GetSupervisorWorder(@Body int LocationNo);
    
    // 작업 일보 조회
    @POST("GetSupervisorWorderSingle")
    Single<SupervisorWorder> GetSupervisorWorderSingle(@Body String supervisorWoNo);

    // AS 아이템 조회
    @POST("GetSupervisorASItemStandardParent")
    Single<List<SupervisorASItemStandard>> GetSupervisorASItemStandardParent();
    @POST("GetWorkTypeByBusinessClassCode")
    Single<List<SupervisorWorkType>> GetWorkTypeByBusinessClassCode(@Body int BusinessClassCode);
    // 작업 일보 사진 불러오기
    @POST("GetSupervisorWoImageData")
    Single<List<SupervisorWoImage>> GetSupervisorWoImageData(@Body String Key);

    // AS 리스트 조회 (계약서)
    @POST("GetSupervisorAsBySupervisorWoNo")
    Single<List<SupervisorAS>> GetSupervisorAsBySupervisorWoNo(@Body String key);

    // AS 단일 조회 (고유번호)
    @POST("GetSupervisorAsBySupervisorAsNo")
    Single<SupervisorAS> GetSupervisorAsBySupervisorAsNo(@Body String SupervisorAsNo);

    // AS 및 추가분 사진 조회
    @POST("GetSupervisorComplaintImageList")
    Single<List<SupervisorComplaintImage>> GetSupervisorComplaintImageList(@Body String itemNo);

    //Insert
    // 작업 일보 작성
    @POST("SetSupervisorWorderData")
    Single<SupervisorWorder> SetSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    // 작업일보 사진 넣기
    @POST("InsertSupervisorWoImageMulti")
    Single<List<SupervisorWoImage>> InsertSupervisorWoImageMulti(@Body List<SupervisorWoImage> Images);

    // 작업일보 사진 넣기
    @POST("InsertSupervisorComplainImageMulti")
    Single<List<SupervisorComplaintImage>> InsertSupervisorComplainImageMulti(@Body List<SupervisorComplaintImage> Images);

    // AS 일지 작성
    @POST("InsertSupervisorAS")
    Single<SupervisorAS> InsertSupervisorAS(@Body SupervisorAS supervisorAS);

    //Update
    // 작업일지 업데이트
    @POST("UpdateSupervisorWorderData")
    Single<SupervisorWorder> UpdateSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    // AS 일지 업데이트
    @POST("UpdateSupervisorAS")
    Single<SupervisorAS> UpdateSupervisorAS(@Body SupervisorAS supervisorAS);

    //Delete
    @POST("DeleteSupervisorWorderData")
    Single<SupervisorWorder> DeleteSupervisorWorderData(@Body SupervisorWorder supervisorWorder);

    @POST("DeleteSupervisorWoImage")
    Single<List<SupervisorWoImage>> DeleteSupervisorWoImage(@Body SupervisorWoImage supervisorWoImage);

    @POST("DeleteSupervisorAS")
    Single<SupervisorAS> DeleteSupervisorAS(@Body SupervisorAS supervisorAS);

    @POST("DeleteSupervisorComplaintImage")
    Single<SupervisorComplaintImage> DeleteSupervisorComplaintImage(@Body SupervisorComplaintImage supervisorComplaintImage);

    // endregion
}
