package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.SupervisorASItemStandardService;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SupervisorASItemStandardViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<SupervisorASItemStandard>> SupervisorASItemStandards = new MutableLiveData<>();
    public SupervisorASItemStandardService service = SupervisorASItemStandardService.getInstance();
    public CompositeDisposable disposable = new CompositeDisposable();

    // AS 아이템 조회
    public void GetSupervisorASItemStandardParent(){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorASItemStandardParent()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorASItemStandard>>(){
                            @Override
                            public void onSuccess(List<SupervisorASItemStandard> supervisorASItemStandards) {
                                loading.setValue(false);
                                if(supervisorASItemStandards.get(0).ErrorCheck == null){
                                    SupervisorASItemStandards.setValue(supervisorASItemStandards);
                                    loadError.setValue(false);
                                }else{
                                    errorMsg.setValue(supervisorASItemStandards.get(0).ErrorCheck);
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
