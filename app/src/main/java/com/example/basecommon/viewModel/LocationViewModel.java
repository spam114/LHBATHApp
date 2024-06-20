package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.LocationService;
import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.CustomLocation;
import com.example.basecommon.model.object.Location;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<CustomLocation>> CustomLocationList = new MutableLiveData<>();

    public LocationService service = LocationService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void GetCustomLocationData(){
        loading.setValue(true);
        disposable.add(
                service.GetCustomLocationNo()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CustomLocation>>(){
                            @Override
                            public void onSuccess(List<CustomLocation> customLocations) {
                                loading.setValue(false);
                                if(customLocations.get(0).ErrorCheck == null){
                                    CustomLocationList.setValue(customLocations);
                                    loadError.setValue(false);
                                }else{
                                    errorMsg.setValue(customLocations.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMsg.setValue(Users.Language==0 ? "서버 오류 발생": "Server error occurred");
                                loadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void GetSearchCustomLocationData(String SearchWord){
        loading.setValue(true);
        disposable.add(
                service.GetSearchCustomLocationNo(SearchWord)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CustomLocation>>(){
                            @Override
                            public void onSuccess(List<CustomLocation> customLocations) {
                                loading.setValue(false);
                                if(customLocations.get(0).ErrorCheck == null){
                                    CustomLocationList.setValue(customLocations);
                                    loadError.setValue(false);
                                }else{
                                    errorMsg.setValue(customLocations.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMsg.setValue(Users.Language==0 ? "서버 오류 발생": "Server error occurred");
                                loadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }
}
