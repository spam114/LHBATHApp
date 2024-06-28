package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.SupervisorAsService;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SupervisorAsViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public SupervisorAsService service = new SupervisorAsService().getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<SupervisorAS>> SupervisorAsList = new MutableLiveData<>();
    public MutableLiveData<SupervisorAS> SingleSupervisorAS = new MutableLiveData<>();

    public void GetSupervisorAsBySupervisorWoNo(String key){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorAsBySupervisorWoNo(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorAS>>(){
                            @Override
                            public void onSuccess(List<SupervisorAS> supervisorASList) {
                                loading.setValue(false);
                                if(supervisorASList.size() == 1 && supervisorASList.get(0).ErrorCheck != null){
                                    errorMsg.setValue(supervisorASList.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SupervisorAsList.setValue(supervisorASList);
                                    loadError.setValue(false);
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

    public void GetSupervisorAsBySupervisorAsNo(String SupervisorAsNo){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorAsBySupervisorAsNo(SupervisorAsNo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorAS>(){
                            @Override
                            public void onSuccess(SupervisorAS item) {
                                loading.setValue(false);
                                if(item.ErrorCheck != null){
                                    errorMsg.setValue(item.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SingleSupervisorAS.setValue(item);
                                    loadError.setValue(false);
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

    // AS 일지 작성
    public void InsertSupervisorAS(SupervisorAS key){
        loading.setValue(true);
        disposable.add(
                service.InsertSupervisorAS(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorAS>(){
                            @Override
                            public void onSuccess(SupervisorAS supervisorAS) {
                                loading.setValue(false);
                                if(!supervisorAS.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorAS.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SingleSupervisorAS.setValue(supervisorAS);
                                    loadError.setValue(false);
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

    // AS 일지 업데이트
    public void UpdateSupervisorAS(SupervisorAS key){
        loading.setValue(true);
        disposable.add(
                service.UpdateSupervisorAS(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorAS>(){
                            @Override
                            public void onSuccess(SupervisorAS supervisorAS) {
                                loading.setValue(false);
                                if(!supervisorAS.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorAS.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SingleSupervisorAS.setValue(supervisorAS);
                                    loadError.setValue(false);
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

    // AS 일지 삭제
    public void DeleteSupervisorAS(SupervisorAS key){
        loading.setValue(true);
        disposable.add(
                service.DeleteSupervisorAS(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorAS>(){
                            @Override
                            public void onSuccess(SupervisorAS supervisorAS) {
                                loading.setValue(false);
                                if(!supervisorAS.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorAS.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SingleSupervisorAS.setValue(supervisorAS);
                                    loadError.setValue(false);
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
