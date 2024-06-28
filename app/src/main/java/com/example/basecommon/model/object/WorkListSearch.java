package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class WorkListSearch {
    @SerializedName("SupervisorCode")
    public int SupervisorCode;

    @SerializedName("SearchKeyWord")
    public String SearchKeyWord="";

    @SerializedName("FromDate")
    public String FromDate="";

    @SerializedName("ToDate")
    public String ToDate="";
}
