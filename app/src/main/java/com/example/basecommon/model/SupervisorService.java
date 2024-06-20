package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorWorkType;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupervisorService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);

    private static SupervisorService instance;
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorService getInstance(){
        if(instance == null){
            instance = new SupervisorService();
        }
        return instance;
    }

    public Single<Supervisor> GetLeaderType(String TelNumber){
        return api.GetLeaderType(TelNumber);
    }

    public Single<List<SupervisorWorkType>> GetWorkTypeByBusinessClassCode(int BusinessClassCode){
        return api.GetWorkTypeByBusinessClassCode(BusinessClassCode);
    }
}
