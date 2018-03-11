package com.example.at;

/**
 * Created by D on 3/9/2018.
 */

public class JSONItem {
    private String imageUrl;
    private String creator;


    public JSONItem(String imageUrls, String creators) {
        imageUrl = imageUrls;
        creator = creators;

    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }




}
