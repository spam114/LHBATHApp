package com.example.basecommon.model.object;

import com.example.basecommon.view.SoundManager;

import java.util.ArrayList;

public class Users {
    public static int VersionCode=-1;
    public static String AndroidID="";
    public static String Model = "";
    public static String PhoneNumber = "";
    public static String DeviceName = "";
    public static String DeviceOS = "";
    //Appversion은 build에서
    public static String Remark = "";
    public static String fromDate="";
    public static String DeptName="";

    public static SoundManager SoundManager;

    public static int REQUEST_SCRAP=4;
    //스캐너관련

    //권한 리스트
    public static ArrayList<Authority> AppAuthorityList = new ArrayList<>();

    //로그인정보
    public static String UserID;
    public static String UserName;
    public static String CustomerCode;  //사업자번호
    public static int DeptCode;
    public static int BusinessClassCode;
    public static int LocationNo;
    public static int Language;

    public static String UserImage;
    //public static int ServiceType;//0:금강공업(음성,진천),1:KKM,2:KKV,3:TEST
    public static String ServiceAddress;
    public static String LoginServiceAddress;
    public static int SeqNo;//재고조사 회차 -1 이면 현재기준 가능한 회차가 셋팅이 되어있지 않다는 뜻

    public static String CostCenter;
    public static String CostCenterName;
}
