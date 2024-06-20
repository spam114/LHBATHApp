package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.SupervisorWoImageService;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SupervisorWoImageViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<SupervisorWoImage>> ImageList = new MutableLiveData<>();
    public SupervisorWoImageService service = SupervisorWoImageService.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();
    private static String Supervisorkey = "";

    public void GetSupervisorWoImageData(String Key){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorWoImageData(Key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorWoImage>>(){
                            @Override
                            public void onSuccess(List<SupervisorWoImage> SupervisorWoImageList) {
                                loading.setValue(false);
                                if(SupervisorWoImageList.size() == 1 && SupervisorWoImageList.get(0).ImageFile.equals("Error")){
                                    errorMsg.setValue(SupervisorWoImageList.get(0).ImageFile);
                                    loadError.setValue(true);
                                }else{
                                    Supervisorkey = Key;
                                    ImageList.setValue(SupervisorWoImageList);
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

    public void InsertSupervisorWoImageMulti(List<SupervisorWoImage> Images){
        loading.setValue(true);
        disposable.add(
                service.InsertSupervisorWoImageMulti(Images)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorWoImage>>(){
                            @Override
                            public void onSuccess(List<SupervisorWoImage> SupervisorWoImageList) {
                                loading.setValue(false);
                                if(SupervisorWoImageList.size() == 1 && SupervisorWoImageList.get(0).ImageFile.equals("False")){
                                    errorMsg.setValue(SupervisorWoImageList.get(0).ImageFile);
                                    loadError.setValue(true);
                                }else{
                                    GetSupervisorWoImageData(Supervisorkey);
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

    public void DeleteSupervisorWoImage(SupervisorWoImage supervisorWoImage){
        loading.setValue(true);
        disposable.add(
                service.DeleteSupervisorWoImage(supervisorWoImage)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorWoImage>>(){
                            @Override
                            public void onSuccess(List<SupervisorWoImage> SupervisorWoImageList) {
                                loading.setValue(false);
                                if(SupervisorWoImageList.size() == 1 && SupervisorWoImageList.get(0).ImageFile.equals("False")){
                                    errorMsg.setValue(SupervisorWoImageList.get(0).ImageFile);
                                    loadError.setValue(true);
                                }else{
                                    ImageList.setValue(SupervisorWoImageList);
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
