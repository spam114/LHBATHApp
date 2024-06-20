package com.example.basecommon.model.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SupervisorWoImage implements Serializable {
    @SerializedName("SupervisorWoNo")
    public String SupervisorWoNo = "";
    @SerializedName("SeqNo")
    public String SeqNo = "";
    @SerializedName("ImageFile")
    public String ImageFile = "";
    @SerializedName("ImageName")
    public String ImageName = "";
    @SerializedName("smallImageFile")
    public String smallImageFile = "";
}
