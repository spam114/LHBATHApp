package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.SupervisorService;
import com.example.basecommon.model.SupervisorWorderService;
import com.example.basecommon.model.object.Supervisor;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.SupervisorWorkType;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.model.object.WorkListSearch;
import com.example.basecommon.model.object.WorkingList;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SupervisorVIewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<SupervisorWorder>> SupervisorWorderList = new MutableLiveData<>();
    public MutableLiveData<Supervisor> supervisorData = new MutableLiveData<>();
    public SupervisorWorderService service = SupervisorWorderService.getInstance();
    public MutableLiveData<SupervisorWorder> supervisorWorderData = new MutableLiveData<>();
    // 작업 일지 조회용
    public MutableLiveData<SupervisorWorder> SingleSupervisorWorder = new MutableLiveData<>();
    public MutableLiveData<List<SupervisorWorkType>> workList = new MutableLiveData<>();
    public MutableLiveData<List<WorkingList>> MyWorkingList = new MutableLiveData<>();
    public SupervisorService supervisorService = SupervisorService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();

    // SupervisorWorder 장소로 검색
    public void GetSupervisorWorder(int LocationNo){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorWorder(LocationNo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorWorder>>(){
                            @Override
                            public void onSuccess(List<SupervisorWorder> supervisorWorders) {
                                loading.setValue(false);
                                if(supervisorWorders.size() == 1 && supervisorWorders.get(0).ErrorCheck != null){
                                    errorMsg.setValue(supervisorWorders.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SupervisorWorderList.setValue(supervisorWorders);
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

    public void GetLeaderType(String TelNumber){
        loading.setValue(true);
        disposable.add(
                supervisorService.GetLeaderType(TelNumber)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Supervisor>(){
                            @Override
                            public void onSuccess(Supervisor supervisor) {
                                loading.setValue(false);
                                if(supervisor.ErrorCheck != null){
                                    errorMsg.setValue(supervisor.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    Users.LeaderType = supervisor.LeaderType;
                                    Users.BusinessClassCode = supervisor.BusinessClassCode;
                                    Users.SupervisorCode = supervisor.SupervisorCode;
                                    supervisorData.setValue(supervisor);
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

    public void GetWorkTypeByBusinessClassCode(int BusinessClassCode){
        loading.setValue(true);
        disposable.add(
                supervisorService.GetWorkTypeByBusinessClassCode(BusinessClassCode)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorWorkType>>(){
                            @Override
                            public void onSuccess(List<SupervisorWorkType> supervisorWorkTypes) {
                                loading.setValue(false);
                                if(supervisorWorkTypes.size() == 1 && supervisorWorkTypes.get(0).ErrorCheck != null){
                                    errorMsg.setValue(supervisorWorkTypes.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    workList.setValue(supervisorWorkTypes);
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

    public void SetSupervisorWorderData(SupervisorWorder supervisorWorder){
        loading.setValue(true);
        disposable.add(
                service.SetSupervisorWorderData(supervisorWorder)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorWorder>(){
                            @Override
                            public void onSuccess(SupervisorWorder supervisorWorder) {
                                loading.setValue(false);
                                if(!supervisorWorder.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorWorder.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    supervisorWorderData.setValue(supervisorWorder);
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

    public void UpdateSupervisorWorderData(SupervisorWorder supervisorWorder){
        loading.setValue(true);
        disposable.add(
                service.UpdateSupervisorWorderData(supervisorWorder)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorWorder>(){
                            @Override
                            public void onSuccess(SupervisorWorder supervisorWorder) {
                                loading.setValue(false);
                                if(!supervisorWorder.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorWorder.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    supervisorWorderData.setValue(supervisorWorder);
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

    public void DeleteSupervisorWorderData(SupervisorWorder supervisorWorder){
        loading.setValue(true);
        disposable.add(
                service.DeleteSupervisorWorderData(supervisorWorder)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorWorder>(){
                            @Override
                            public void onSuccess(SupervisorWorder supervisorWorder) {
                                loading.setValue(false);
                                if(!supervisorWorder.ErrorCheck.equals("")){
                                    errorMsg.setValue(supervisorWorder.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    supervisorWorderData.setValue(supervisorWorder);
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

    // 작업 일보 조회 ( 간략 대량 )
    public void GetWorkingList(WorkListSearch workListSearch){
        loading.setValue(true);
        disposable.add(
                service.GetWorkingList(workListSearch)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<WorkingList>>(){
                            @Override
                            public void onSuccess(List<WorkingList> workingLists) {
                                if(workingLists.size() == 1 && workingLists.get(0).ErrorCheck != null){
                                    errorMsg.setValue(workingLists.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                    loading.setValue(false);
                                }else{
                                    MyWorkingList.setValue(workingLists);
                                    loadError.setValue(false);
                                    loading.setValue(false);
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

    // 작업 일보 조회 ( 세부 단일 )
    public void GetSupervisorWorderSingle(String supervisorWoNo){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorWorderSingle(supervisorWoNo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorWorder>(){
                            @Override
                            public void onSuccess(SupervisorWorder supervisorWorder) {
                                loading.setValue(false);
                                if(supervisorWorder.ErrorCheck != null){
                                    errorMsg.setValue(supervisorWorder.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    SingleSupervisorWorder.setValue(supervisorWorder);
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
