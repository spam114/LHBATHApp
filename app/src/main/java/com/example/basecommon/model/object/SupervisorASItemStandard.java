package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class SupervisorASItemStandard {
    @SerializedName("ErrorCheck")
    public String ErrorCheck="";

    @SerializedName("StandardNo")
    public int StandardNo;

    @SerializedName("Name")
    public String Name="";

    @SerializedName("ParentNo")
    public int ParentNo;
}
