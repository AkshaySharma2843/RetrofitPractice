package com.retex.retrofitpractice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("col_lead_id")
    @Expose
    private String colLeadId;
    @SerializedName("user_image")
    @Expose
    private String userImage;

    public ImageModel(String id, String colLeadId, String userImage) {
        this.id = id;
        this.colLeadId = colLeadId;
        this.userImage = userImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColLeadId() {
        return colLeadId;
    }

    public void setColLeadId(String colLeadId) {
        this.colLeadId = colLeadId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

}
