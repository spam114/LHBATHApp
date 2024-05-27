package com.example.basecommon.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.databinding.ActivityLoginBinding;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.view.BackPressControl;
import com.example.basecommon.view.PreferenceManager;
import com.example.basecommon.viewModel.LoginViewModel;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    BackPressControl backpressed;
    LoginViewModel loginViewModel;
    boolean firstFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setServiceAddress();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.tlID.setErrorEnabled(true);
        binding.tlPW.setErrorEnabled(true);
        this.firstFlag = getIntent().getBooleanExtra("FirstFlag", true);
        backpressed = new BackPressControl(this);
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);
        setView();
        initEvent();
        observerViewModel();
        StartLogin();
    }

    private void setView() {
        int langInt = PreferenceManager.getInt(this, "Language");
        if (langInt == 0) {
            binding.rbKor.setChecked(true);
        } else {
            binding.rbEng.setChecked(true);
        }
    }

    private void setServiceAddress() {
        Users.ServiceAddress = CommonApplication.getResourses().getString(R.string.service_address);

    }

    //로그인을 성공하면 무조건 이 메소드를 쓴다.
    private void successLogin() {
        if (binding.checkAuto.isChecked()) {
            PreferenceManager.setBoolean(this, "AutoLogin", true);
            PreferenceManager.setString(this, "ID", binding.edtID.getText().toString());
            PreferenceManager.setString(this, "PW", binding.edtPW.getText().toString());
        }
        loginViewModel.GetUserImage();
    }


    private void initEvent() {
        // 키보드 엔터 키 이벤트 생성
        binding.edtID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) { // IME_ACTION_SEARCH , IME_ACTION_GO
                    binding.edtPW.requestFocus();
                }
                return false;
            }
        });
        binding.edtPW.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;

            }
        });

        // 포커스 이벤트
        binding.edtID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!binding.edtID.getText().toString().isEmpty()) {
                        binding.tlID.setErrorEnabled(false);
                        binding.tlID.setError(null);
                    }
                }
            }
        });

        binding.edtPW.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!binding.edtPW.getText().toString().isEmpty()) {
                        binding.tlPW.setErrorEnabled(false);
                        binding.tlPW.setError(null);
                    }
                }
            }
        });
    }

    public void restart(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }


    /**
     * 로그인 버튼 클릭
     *
     * @param v
     */
    public void btnLoginClick(View v) {
        if (binding.rbKor.isChecked())
            PreferenceManager.setInt(this, "Language", 0);
        else
            PreferenceManager.setInt(this, "Language", 1);
        StartLogin();
    }

    private void StartLogin() {
        SearchCondition sc = new SearchCondition();
        boolean autoLoginFlag = PreferenceManager.getBoolean(this, "AutoLogin");
        if (autoLoginFlag) {
            sc.UserID = PreferenceManager.getString(this, "ID");
            sc.PassWord = PreferenceManager.getString(this, "PW");
            sc.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
            sc.Language = PreferenceManager.getInt(this, "Language");
        } else {
            if (this.firstFlag) {
                GetLoginInfo();
                return;
            } else {
                sc.UserID = CheckInputLoginUserID(); // 아이디 공백 확인
                sc.PassWord = CheckInputLoginPassword(); // 패스워드 공백 확인
                sc.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
                if (binding.rbKor.isChecked())
                    sc.Language = 0;
                else
                    sc.Language = 1;
                if (sc.UserID.equals("") || sc.PassWord.equals("")) return;
            }
        }
        loginViewModel.GetLoginInfo(sc);
    }

    private void GetLoginInfo() {//폰번호로 로그인
        SearchCondition sc = new SearchCondition();
        sc.UserID = Users.PhoneNumber;
        sc.Language = PreferenceManager.getInt(this, "Language");
        sc.AppCode = CommonApplication.getResourses().getString(R.string.app_code);
        sc.PassWord="";
        this.firstFlag = false;
        loginViewModel.GetLoginInfo(sc);
    }

    // 로그인 시 ID와 Password가 공백인지 확인한다.
    private String CheckInputLoginUserID() {
        String userID = binding.edtID.getText().toString();
        if (userID.isEmpty()) {
            binding.tlID.setErrorEnabled(true);
            //binding.tlID.setError("아이디를 입력해주세요.");
            binding.tlID.setError(" ");
        } else {
            binding.tlID.setError(null);
            binding.tlID.setErrorEnabled(false);
        }
        return userID;
    }

    private String CheckInputLoginPassword() {
        String passWord = binding.edtPW.getText().toString();
        if (passWord.isEmpty()) {
            binding.tlPW.setErrorEnabled(true);
            //binding.tlPW.setError("패스워드를 입력해주세요.");
            binding.tlPW.setError(" ");
        } else {
            binding.tlPW.setError(null);
            binding.tlPW.setErrorEnabled(false);
        }
        return passWord;
    }

    public void observerViewModel() {
        loginViewModel.userImage.observe(this, models -> {
            if (models != null) {
                Users.UserImage = models;
            } else
                Users.UserImage = "fail";
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //성공, 실패 여부
        loginViewModel.loginFail.observe(this, models -> {
            if (models != null) {
                if (!models) {//성공
                    successLogin();
                }
            }
        });

        //에러메시지
        loginViewModel.errorMsg.observe(this, models -> {
            if (models != null) {
                if (!models.equals(""))
                    Toast.makeText(this, models, Toast.LENGTH_SHORT).show();
                PreferenceManager.setBoolean(this, "AutoLogin", false);
                PreferenceManager.setString(this, "ID", "");
                PreferenceManager.setString(this, "PW", "");
                PreferenceManager.setString(this, "PCCode", "");
                //PreferenceManager.setInt(this, "Language", 0);
                progressOFF2();
            }
        });

        loginViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {//로딩중
                    startProgress();
                } else {//로딩끝
                    progressOFF2();
                }
            }
        });
    }

    private void startProgress() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressOFF2();
            }
        }, 5000);
        progressON("Loading...", handler);
    }

    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            backpressed.onBackPressed();
        }
    };
}
