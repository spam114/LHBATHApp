package com.example.basecommon.model;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppVersionService {
    private static final String BASE_URL = Users.LoginServiceAddress;
    private static AppVersionService instance;//todo
    public static DataApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // json 데이터를 java object로 변형해줌
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi.class);


    public static AppVersionService getInstance() {
        if (instance == null) {
            instance = new AppVersionService();

            //통신에러 로그 확인을 위한 코드
            /*OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create()) // json 데이터를 java object로 변형해줌
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(DataApi.class);*/
        }
        return instance;
    }

    public Single<AppVersion> CheckAppVersion(SearchCondition searchCondition) {// 객체의 사용//todo
        return api.CheckAppVersion(searchCondition);//todo
    }

    public Single<List<AppVersion>> checkAppProgramsPowerAndLoginHistory(SearchCondition searchCondition) {// 객체의 사용//todo
        return api.checkAppProgramsPowerAndLoginHistory(searchCondition);//todo
    }

    //InsertAppLoginHistory
    public Single<AppVersion> InsertAppLoginHistory(SearchCondition searchCondition) {// 객체의 사용//todo
        return api.InsertAppLoginHistory(searchCondition);//todo
    }

    public Single<AppVersion> GetNoticeData2() {
        SearchCondition sc = new SearchCondition();
        sc.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
        return api.GetNoticeData2(sc);
    }
}
