package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.AppVersionService;
import com.example.basecommon.model.object.AppVersion;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AppVersionViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<AppVersion> versionlist = new MutableLiveData<>();
    public MutableLiveData<AppVersion> userDataList = new MutableLiveData<>();
    public MutableLiveData<AppVersion> noticeData = new MutableLiveData<>();//NoticeData 변경점
    public AppVersionService service = AppVersionService.getInstance();
    // os에 의해 앱의 프로세스가 죽거는 등의 상황에서
    // Single 객체를 가로채기 위함
    private CompositeDisposable disposable = new CompositeDisposable();


    // 뷰에서 데이터를 가져오기 위해 호출하는 함수

    public void CheckAppVersion() {
        // 서버로부터 데이터를 받아오는 동안에 로딩 스피너를 보여주기 위
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
        loading.setValue(true);
        disposable.add(
                // service.getData()를 Unit Test에서 다루기 힘듬 -> 서비스를 새로운 스레드에서 구하기 때문
                // exedcutor rules
                // 백그라운드 스레드를 호툴할 때 즉시 안드로이드 스케줄를 위한 같은 것을 반환한다.
                service.CheckAppVersion(searchCondition) // Single<List<T>>를 반환한다.
                        .subscribeOn(Schedulers.newThread()) // 새로운 스레드에서 통신한다.
                        .observeOn(AndroidSchedulers.mainThread()) // 응답 값을 가지고 ui update를 하기 위해 필요함, 메인스레드와 소통하기 위
                        .subscribeWith(new DisposableSingleObserver<AppVersion>() {
                            @Override
                            public void onSuccess(@NonNull AppVersion models) {
                                loading.setValue(false);
                                if(models.ErrorCheck!=null){
                                    errorMsg.setValue(models.ErrorCheck);
                                    loadError.setValue(true);
                                }
                                else{
                                    versionlist.setValue(models);
                                    loadError.setValue(false);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                errorMsg.setValue(Users.Language==0 ? "서버 오류 발생": "Server error occurred");
                                loadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void InsertAppLoginHistory() {
        // 서버로부터 데이터를 받아오는 동안에 로딩 스피너를 보여주기 위

        SearchCondition searchCondition = new SearchCondition();
        searchCondition.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
        searchCondition.AndroidID = Users.AndroidID;
        searchCondition.Model = Users.Model;
        searchCondition.PhoneNumber = Users.PhoneNumber;
        searchCondition.DeviceName = Users.DeviceName;
        searchCondition.DeviceOS = Users.DeviceOS;
        searchCondition.AppVersion = Integer.toString(Users.VersionCode);
        searchCondition.Remark = "";
        searchCondition.UserID = Users.PhoneNumber;

        loading.setValue(true);
        disposable.add(
                // service.getData()를 Unit Test에서 다루기 힘듬 -> 서비스를 새로운 스레드에서 구하기 때문
                // exedcutor rules
                // 백그라운드 스레드를 호툴할 때 즉시 안드로이드 스케줄를 위한 같은 것을 반환한다.
                service.InsertAppLoginHistory(searchCondition) // Single<List<T>>를 반환한다.
                        .subscribeOn(Schedulers.newThread()) // 새로운 스레드에서 통신한다.
                        .observeOn(AndroidSchedulers.mainThread()) // 응답 값을 가지고 ui update를 하기 위해 필요함, 메인스레드와 소통하기 위
                        .subscribeWith(new DisposableSingleObserver<AppVersion>() {
                            @Override
                            public void onSuccess(@NonNull AppVersion models) {
                                loading.setValue(false);
                                if(models.ErrorCheck!=null){
                                    errorMsg.setValue(models.ErrorCheck);
                                    loadError.setValue(true);
                                }
                                else{
                                    userDataList.setValue(models);
                                    loadError.setValue(false);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                errorMsg.setValue(Users.Language==0 ? "서버 오류 발생": "Server error occurred");
                                loadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }
    public void GetNoticeData2() {
        loading.setValue(true);
        disposable.add(service.GetNoticeData2()
                .subscribeOn(Schedulers.newThread()) // 새로운 스레드에서 통신한다.
                .observeOn(AndroidSchedulers.mainThread()) // 응답 값을 가지고 ui update를 하기 위해 필요함, 메인스레드와 소통하기 위
                .subscribeWith(new DisposableSingleObserver<AppVersion>() {
                    @Override
                    public void onSuccess(@NonNull AppVersion models) {
                        loading.setValue(false);
                        if (models.ErrorCheck != null) {//서버에서 직접 지정한 에러
                            errorMsg.setValue(models.ErrorCheck);
                        }
                        else{//성공
                            noticeData.setValue(models);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        errorMsg.setValue(Users.Language == 0 ? "서버 오류 발생" : "Server error occurred");
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        // 앱이 통신 중에 프로세스가 종료될 경우(앱이 destory됨)
        // 메모리 손실을 최소화 하기 위해 백그라운드 스레드에서 통신 작업을 중단한다.
        disposable.clear();
    }
}
