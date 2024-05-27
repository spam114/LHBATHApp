package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.LoginService;
import com.example.basecommon.model.object.LoginInfo;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<Boolean> loginFail = new MutableLiveData<>();//로그인 성공여부 true: 실패, false: 성공
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<String> userImage = new MutableLiveData<>();//유저 사진정보

    // 서버 객체 호출 : api를 통해 서버와 연결된다.
    public LoginService service = LoginService.getInstance();//todo
    // Single 객체를 가로채기 위함
    private CompositeDisposable disposable = new CompositeDisposable();



    public void GetUserImage() {
        loading.setValue(true);
        disposable.add(service.GetUserImage()
                .subscribeOn(Schedulers.newThread()) // 새로운 스레드에서 통신한다.
                .observeOn(AndroidSchedulers.mainThread()) // 응답 값을 가지고 ui update를 하기 위해 필요함, 메인스레드와 소통하기 위
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(@NonNull String models) {
                        userImage.setValue(models);
                        loading.setValue(false);
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

    // 로그인 정보 호출
    public void GetLoginInfo(SearchCondition sc) {
        loading.setValue(true);
        disposable.add(
                service.GetLoginInfo(sc) // POP 로그인 정보를 확인한다.
                        .subscribeOn(Schedulers.newThread()) // 새로운 스레드에서 통신한다.
                        .observeOn(AndroidSchedulers.mainThread()) // 응답 값을 가지고 ui update를 하기 위해 필요함, 메인스레드와 소통하기 위
                        .subscribeWith(new DisposableSingleObserver<LoginInfo>() {//todo

                            @Override
                            public void onSuccess(@NonNull LoginInfo models) {//todo
                                loading.setValue(false);
                                if (models.ErrorCheck != null) {
                                    errorMsg.setValue(models.ErrorCheck);
                                    loginFail.setValue(true);
                                    //phoneNumberFlag.setValue(false);
                                } else {
                                    Users.UserID = sc.UserID;
                                    Users.UserName = models.UserName;
                                    Users.DeptCode = models.DeptCode;
                                    Users.CostCenter = models.CostCenter;
                                    Users.CostCenterName = models.CostCenterName;
                                    //Users.CustomerCode = models.CustomerCode;
                                    //Users.BusinessClassCode = models.BusinessClassCode;
                                    //Users.GboutSourcing = models.GboutSourcing;
                                    Users.Language = sc.Language;
                                    Users.AppAuthorityList = models.AppAuthorityList;
                                    loginFail.setValue(false);
                                    //phoneNumberFlag.setValue(true);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                //phoneNumberFlag.setValue(false);
                                errorMsg.setValue(Users.Language == 0 ? "서버 오류 발생" : "Server error occurred");
                                loginFail.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }
}
