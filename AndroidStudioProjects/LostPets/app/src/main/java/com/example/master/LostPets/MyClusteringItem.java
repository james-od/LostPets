package com.example.master.LostPets;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyClusteringItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;

    public MyClusteringItem(String name, String gender, String regLost, String postCode, String breed, double latitude, double longitude, long id, String imageAddress) {
        mPosition = new LatLng(latitude, longitude);
        mTitle = name;
        mSnippet = breed+" " + regLost+" " +id;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}