package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

public class Supervisor {
    @SerializedName("ErrorCheck")
    public String ErrorCheck="";

    @SerializedName("SupervisorCode")
    public int SupervisorCode;

    @SerializedName("SupervisorName")
    public String SupervisorName="";

    @SerializedName("LeaderType")
    public int LeaderType;

    @SerializedName("DeptName")
    public String DeptName="";

    @SerializedName("BusinessClassCode")
    public int BusinessClassCode;

    @SerializedName("EmployeeNo")
    public String EmployeeNo="";

}
