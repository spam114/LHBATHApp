package com.example.basecommon.model.object;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CustomLocation{
    @SerializedName("ErrorCheck")
    public String ErrorCheck=null;

    @SerializedName("LocationNo")
    public int LocationNo;

    @SerializedName("LocationName")
    public String LocationName="";

    @SerializedName("CustomerName")
    public String CustomerName="";

    @SerializedName("CustomerCode")
    public String CustomerCode="";

    @SerializedName("ContractNo")
    public String ContractNo="";

    @Override
    public boolean equals(@Nullable Object obj) {
        CustomLocation customLocation = (CustomLocation) obj;

        if(customLocation.LocationName == this.LocationName){
            return true;
        }

        return false;
    }
}
