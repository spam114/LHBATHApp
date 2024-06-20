package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class SupervisorWorkType {
    @SerializedName("ErrorCheck")
    public String ErrorCheck="";

    @SerializedName("WorkTypeCode")
    public int WorkTypeCode;

    @SerializedName("WorkTypeName")
    public String WorkTypeName="";

    @SerializedName("SeqNo")
    public int SeqNo;

    @SerializedName("BusinessClassCode")
    public int BusinessClassCode;
}
