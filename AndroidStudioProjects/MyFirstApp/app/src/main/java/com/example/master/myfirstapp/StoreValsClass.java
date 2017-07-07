package com.example.master.myfirstapp;

import java.util.ArrayList;

/**
 * Created by Master on 08-Oct-16.
 */
public class StoreValsClass {

    public static ArrayList<GPSTestResultsArr> GPStra4 = new ArrayList<>();
    public static String name;
    public static String gender;
    public static String regLost;
    public static String postCode;
    public static String breed;
    public static double lat;
    public static double longi;
    public static long id;
    public static String imageAddress;



    public StoreValsClass(){

    }
    public static void addSet(String name, String gender, String regLost, String postCode, String breed, double lat, double longi, long id, String imageAddress){

        GPStra4.add(new GPSTestResultsArr(name, gender, regLost, postCode, breed, lat, longi, id, imageAddress));


    }
    public ArrayList<GPSTestResultsArr> returnSet(){
        return GPStra4;
    }

    public String toString(){
        String temp="";
        for(int i=0;i<GPStra4.size();i++){
            temp = temp + GPStra4.get(i).toString();
        }
        return(temp);
    }
}
