package com.example.ayabeltran.firstproject;

/**
 * Created by ayabeltran on 31/01/2018.
 */

public class Place {

    private int id;
    private byte[] photo;
    private String name;
    private String des;

    public Place(int id, byte[] photo, String name, String des) {

        this.id = id;
        this.photo = photo;
        this.name = name;
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

