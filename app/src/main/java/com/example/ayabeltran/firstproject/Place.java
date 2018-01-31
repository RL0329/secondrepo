package com.example.ayabeltran.firstproject;

/**
 * Created by ayabeltran on 31/01/2018.
 */

public class Place {

    private int id;
    private String name;
    private String description;
    private int imageId;

    public Place(int id, String name, String price, String description, int imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

