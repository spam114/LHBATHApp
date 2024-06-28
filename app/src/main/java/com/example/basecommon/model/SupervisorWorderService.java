package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.WorkListSearch;
import com.example.basecommon.model.object.WorkingList;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class SupervisorWorderService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);
    private static SupervisorWorderService instance;
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorWorderService getInstance(){
        if(instance == null){
            instance = new SupervisorWorderService();
        }
        return instance;
    }

    // SupervisorWorder 장소로 검색
    public Single<List<SupervisorWorder>> GetSupervisorWorder(int LocationNo){
        return api.GetSupervisorWorder(LocationNo);
    }
    public Single<SupervisorWorder> SetSupervisorWorderData(SupervisorWorder supervisorWorder){
        return api.SetSupervisorWorderData(supervisorWorder);
    }

    public Single<SupervisorWorder> UpdateSupervisorWorderData(SupervisorWorder supervisorWorder){
        return api.UpdateSupervisorWorderData(supervisorWorder);
    }
    public Single<SupervisorWorder> DeleteSupervisorWorderData(SupervisorWorder supervisorWorder){
        return api.DeleteSupervisorWorderData(supervisorWorder);
    }
    
    // 작업 일보 조회 ( 간략 대량 )
    public Single<List<WorkingList>> GetWorkingList(WorkListSearch workListSearch){
        return api.GetWorkingList(workListSearch);
    }

    // 작업 일보 조회 ( 세부 사항 )
    public Single<SupervisorWorder> GetSupervisorWorderSingle(String SupervisorWoNO){
        return api.GetSupervisorWorderSingle(SupervisorWoNO);
    }
}
