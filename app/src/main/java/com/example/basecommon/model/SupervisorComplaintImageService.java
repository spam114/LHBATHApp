package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorComplaintImage;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupervisorComplaintImageService {
    private static final String BASE_URL = CommonApplication.getResourses().getString(R.string.Test_address);

    private static SupervisorComplaintImageService instance;

    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);

    public static SupervisorComplaintImageService getInstance(){
        if(instance == null){
            instance = new SupervisorComplaintImageService();
        }
        return instance;
    }

    public Single<List<SupervisorComplaintImage>> GetSupervisorComplaintImageList(String ItemNo){
        return api.GetSupervisorComplaintImageList(ItemNo);
    }

    public Single<List<SupervisorComplaintImage>> InsertSupervisorComplainImageMulti(List<SupervisorComplaintImage> Items){
        return api.InsertSupervisorComplainImageMulti(Items);
    }

    public Single<SupervisorComplaintImage> DeleteSupervisorComplaintImage(SupervisorComplaintImage Item){
        return api.DeleteSupervisorComplaintImage(Item);
    }

}
