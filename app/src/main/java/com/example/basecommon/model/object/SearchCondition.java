package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class SearchCondition {
    @SerializedName("AppCode")
    public String AppCode;

    @SerializedName("AndroidID")
    public String AndroidID;

    @SerializedName("Model")
    public String Model;

    @SerializedName("PhoneNumber")
    public String PhoneNumber;

    @SerializedName("DeviceName")
    public String DeviceName;

    @SerializedName("DeviceOS")
    public String DeviceOS;

    @SerializedName("AppVersion")
    public String AppVersion;

    @SerializedName("Remark")
    public String Remark;

    @SerializedName("SaleType")
    public String SaleType;

    @SerializedName("UserID")
    public String UserID;

    @SerializedName("PassWord")
    public String PassWord;

    @SerializedName("BusinessClassCode")
    public int BusinessClassCode;

    @SerializedName("CustomerCode")
    public String CustomerCode;

    @SerializedName("Barcode")
    public String Barcode;

    @SerializedName("LocationNo")
    public int LocationNo;

    @SerializedName("Language")
    public int Language;

    @SerializedName("Zone")
    public String Zone;

    @SerializedName("ItemTag")
    public String ItemTag;

    @SerializedName("ZoneSeqNo")
    public String ZoneSeqNo;

    @SerializedName("SeqNo")
    public String SeqNo;

    @SerializedName("CostCenter")
    public String CostCenter;

    public SearchCondition(){}
}
