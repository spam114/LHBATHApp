package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorWorkType;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupervisorAsService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);

    private static SupervisorAsService instance;
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorAsService getInstance(){
        if(instance == null){
            instance = new SupervisorAsService();
        }
        return instance;
    }

    // AS 작업 보기
    public Single<List<SupervisorAS>> GetSupervisorAsBySupervisorWoNo(String key){
        return api.GetSupervisorAsBySupervisorWoNo(key);
    }

    // AS 작업 단일 조회
    public Single<SupervisorAS> GetSupervisorAsBySupervisorAsNo(String SupervisorAsNo){
        return api.GetSupervisorAsBySupervisorAsNo(SupervisorAsNo);
    }
    
    // AS 일지 작성
    public Single<SupervisorAS> InsertSupervisorAS(SupervisorAS key){
        return api.InsertSupervisorAS(key);
    }

    // AS 일지 업데이트
    public Single<SupervisorAS> UpdateSupervisorAS(SupervisorAS key){
        return api.UpdateSupervisorAS(key);
    }

    // AS 일지 삭제
    public Single<SupervisorAS> DeleteSupervisorAS(SupervisorAS key){
        return api.DeleteSupervisorAS(key);
    }
}
