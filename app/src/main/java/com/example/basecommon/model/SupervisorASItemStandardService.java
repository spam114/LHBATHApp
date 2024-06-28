package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.SupervisorASItemStandard;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupervisorASItemStandardService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);
    private static SupervisorASItemStandardService instance;
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorASItemStandardService getInstance(){
        if(instance == null){
            instance = new SupervisorASItemStandardService();
        }
        return instance;
    }

    public Single<List<SupervisorASItemStandard>> GetSupervisorASItemStandardParent(){
        return api.GetSupervisorASItemStandardParent();
    }
}
