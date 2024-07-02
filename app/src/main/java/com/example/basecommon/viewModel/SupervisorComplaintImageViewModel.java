package com.example.basecommon.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecommon.model.SupervisorComplaintImageService;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.SupervisorComplaintImage;
import com.example.basecommon.model.object.Users;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SupervisorComplaintImageViewModel extends ViewModel {
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();// 데이터를 가져오는 것에 성공했는지를 알려주는 데이터(앱버전)
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();//로딩바 상태 true: 유지, false: 종료
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();//에러메시지
    public MutableLiveData<List<SupervisorComplaintImage>> Images = new MutableLiveData<>();
    public SupervisorComplaintImageService service = SupervisorComplaintImageService.getInstance();
    public CompositeDisposable disposable = new CompositeDisposable();
    private static String key = "";

    // AS 및 추가분 사진 조회
    public void GetSupervisorComplaintImageList(String itemNo){
        loading.setValue(true);
        disposable.add(
                service.GetSupervisorComplaintImageList(itemNo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorComplaintImage>>(){
                            @Override
                            public void onSuccess(List<SupervisorComplaintImage> SupervisorComplaintImages) {
                                loading.setValue(false);
                                if(SupervisorComplaintImages.size() == 1 && SupervisorComplaintImages.get(0).ErrorCheck != null){
                                    errorMsg.setValue(SupervisorComplaintImages.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    key = itemNo;
                                    Images.setValue(SupervisorComplaintImages);
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

    public void InsertSupervisorComplainImageMulti(List<SupervisorComplaintImage> Items){
        loading.setValue(true);
        disposable.add(
                service.InsertSupervisorComplainImageMulti(Items)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<SupervisorComplaintImage>>(){
                            @Override
                            public void onSuccess(List<SupervisorComplaintImage> SupervisorComplaintImages) {
                                loading.setValue(false);
                                if(SupervisorComplaintImages.size() == 1 && SupervisorComplaintImages.get(0).ErrorCheck != null){
                                    errorMsg.setValue(SupervisorComplaintImages.get(0).ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    GetSupervisorComplaintImageList(key);
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

    public void DeleteSupervisorComplaintImage(SupervisorComplaintImage Item){
        loading.setValue(true);
        disposable.add(
                service.DeleteSupervisorComplaintImage(Item)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<SupervisorComplaintImage>(){
                            @Override
                            public void onSuccess(SupervisorComplaintImage SupervisorComplaintImage) {
                                loading.setValue(false);
                                if(SupervisorComplaintImage.ErrorCheck != null){
                                    errorMsg.setValue(SupervisorComplaintImage.ErrorCheck);
                                    loadError.setValue(true);
                                }else{
                                    GetSupervisorComplaintImageList(key);
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
