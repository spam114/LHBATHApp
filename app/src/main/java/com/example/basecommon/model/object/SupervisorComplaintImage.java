package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

// 추가분 및 AS 사진 데이터
public class SupervisorComplaintImage {
    @SerializedName("ErrorCheck")
    public String ErrorCheck="";

    @SerializedName("itemNo")
    public String itemNo="";

    @SerializedName("No")
    public int No;
    @SerializedName("Type")
    public int Type;

    @SerializedName("ImageName")
    public String ImageName="";

    @SerializedName("ImageFile")
    public String ImageFile="";

    @SerializedName("InsertTime")
    public String InsertTime="";

    @SerializedName("UpdateTime")
    public String UpdateTime="";

    @SerializedName("InsertUser")
    public int InsertUser;

    @SerializedName("UpdateUser")
    public int UpdateUser;

}
