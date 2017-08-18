package com.example.master.LostPets;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

class CustomClusterRenderer extends DefaultClusterRenderer<MyClusteringItem> {

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyClusteringItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected int getColor(int clusterSize) {
        return  Color.parseColor("#ff6666");
    }

    @Override
    protected void onBeforeClusterItemRendered(MyClusteringItem item,
                                               MarkerOptions markerOptions) {

        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_paw));
        markerOptions.title(item.getTitle());
        markerOptions.snippet(item.getSnippet());
}

    /*
    @Override
    protected void onBeforeClusterItemRendered(T item, MarkerOptions markerOptions) {
        // Use this method to set your own icon for the markers
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster, MarkerOptions markerOptions) {
        // Use this method to set your own icon for the clusters
    }
    */
}