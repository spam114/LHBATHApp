package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.SearchCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContractService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);
    private static ContractService instance;

    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static ContractService getInstance(){
        if(instance == null){
            instance = new ContractService();
        }
        return instance;
    }

    public Single<List<Contract>> GetSearchLocationData(int LocationNo){
        return api.GetSearchLocationData(LocationNo);
    }

}
