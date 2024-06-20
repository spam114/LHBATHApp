package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.ContractService;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class ContractViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<Contract>> contractData = new MutableLiveData<>();
    public ContractService service = ContractService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void GetSearchLocationData(int locationNo){
        loading.setValue(true);
        disposable.add(
                service.GetSearchLocationData(locationNo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Contract>>(){
                            @Override
                            public void onSuccess(List<Contract> contracts) {
                                loading.setValue(false);
                                if(contracts.size() !=0 && contracts.get(0).ErrorCheck != null){
                                    errorMsg.setValue("데이터 연결에 실패하였습니다.");
                                    loadError.setValue(true);
                                }else{
                                    contractData.setValue(contracts);
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
