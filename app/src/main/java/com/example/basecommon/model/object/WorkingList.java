package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class WorkingList {
    @SerializedName("ErrorCheck")
    public String ErrorCheck=null;

    @SerializedName("SupervisorWoNo")
    public String SupervisorWoNo="";

    @SerializedName("WorkDate")
    public String WorkDate="";

    @SerializedName("Dong")
    public String Dong="";

    @SerializedName("WorkTypeName")
    public String WorkTypeName="";

    @SerializedName("SuperVisorName")
    public String SuperVisorName="";

    @SerializedName("LocationName")
    public String LocationName="";

    @SerializedName("LocationNo")
    public String LocationNo="";

    @SerializedName("CustomerCode")
    public String CustomerCode="";

    @SerializedName("CustomerName")
    public String CustomerName="";

    @SerializedName("StatusFlag")
    public int StatusFlag;

    @SerializedName("ContractNo")
    public String ContractNo;

    @SerializedName("WorkTypeCode")
    public int WorkTypeCode;


}
