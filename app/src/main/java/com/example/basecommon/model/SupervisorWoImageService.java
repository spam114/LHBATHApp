package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.model.object.SupervisorWorkType;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupervisorWoImageService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);

    private static SupervisorWoImageService instance;
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorWoImageService getInstance(){
        if(instance == null){
            instance = new SupervisorWoImageService();
        }
        return instance;
    }

    public Single<List<SupervisorWoImage>> GetSupervisorWoImageData(String Key){
        return api.GetSupervisorWoImageData(Key);
    }

    public Single<List<SupervisorWoImage>> InsertSupervisorWoImageMulti(List<SupervisorWoImage> Images){
        return api.InsertSupervisorWoImageMulti(Images);
    }

    public Single<List<SupervisorWoImage>> DeleteSupervisorWoImage(SupervisorWoImage supervisorWoImage){
        return api.DeleteSupervisorWoImage(supervisorWoImage);
    }
}
