package com.retex.retrofitpractice;

public class ImageModel {
    String id;
    String col_lead_id;
    String user_image;

    public ImageModel(String id, String col_lead_id, String user_image) {
        this.id = id;
        this.col_lead_id = col_lead_id;
        this.user_image = user_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCol_lead_id() {
        return col_lead_id;
    }

    public void setCol_lead_id(String col_lead_id) {
        this.col_lead_id = col_lead_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
