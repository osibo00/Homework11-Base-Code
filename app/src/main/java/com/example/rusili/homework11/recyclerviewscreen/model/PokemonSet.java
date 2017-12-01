package com.example.rusili.homework11.recyclerviewscreen.model;

/**
 * Created by oleg on 12/1/17.
 */

public class PokemonSet {

    private int number;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlSplit = url.split("/");
        return Integer.parseInt(urlSplit[urlSplit.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
