package com.example.master.myfirstapp;

import java.util.ArrayList;

/**
 * Created by Master on 08-Oct-16.
 */


public class GPSTestResultsArr {
/*
    public double latitude;
    public double longitude;
    public int petId;
    public String petName;
    public String Species;
    public String description;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLatitude() {

        return latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecies() {

        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public String getPetName() {

        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetId() {

        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public double getLongitude() {

        return longitude;
    }
    public String toString(){
        return "" +getLatitude()+" "+getLongitude()+" "+getPetId()+" "+petName+" "+getSpecies()+""+getDescription();
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GPSTestResultsArr(double lat, double longi, int petid, String petNam, String Species, String desc){
        this.latitude=lat;
        this.longitude=longi;
        this.petId=petid;
        this.petName=petNam;
        this.Species=Species;
        this.description=desc;
    }
    */
    public String Name;
    public String Gender;
    public String RegionLost;
    public String PostCode;
    public String Breed;
    public double latitude;
    public double longitude;
    public long id;
    public String imageAddress;

    public void setName(String name){
        this.Name = name;
    }
    public void setGender(String Gender){
        this.Gender = Gender;
    }
    public void serRegionLost(String RegionLost){
        this.RegionLost = RegionLost;
    }
    public void setPostCode(String PostCode){
        this.PostCode = PostCode;
    }
    public void setBreed(String Breed){
        this.Breed = Breed;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setimageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getName(){
        return Name;
    }
    public String getGender(){
        return Gender;
    }
    public String getRegionLost(){
        return RegionLost;
    }
    public String getPostCode(){
        return PostCode;
    }
    public String getBreed(){
        return Breed;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public long getId() {
        return id;
    }
    public String getImageAddress() {
        return imageAddress;
    }

    public String toString(){
        return ""+getName()+" "+getGender()+" "+getRegionLost()+" "+getPostCode()+" "+getBreed()+" "+getLatitude()+" "+getLongitude()+" "+getId()+" "+getImageAddress();
    }

    public GPSTestResultsArr(String name, String gender, String regLost, String postCode, String breed, double lat, double longi, long id, String imageAddress){
        this.Name=name;
        this.Gender=gender;
        this.RegionLost=regLost;
        this.PostCode=postCode;
        this.Breed=breed;
        this.latitude=lat;
        this.longitude=longi;
        this.id=id;
        this.imageAddress=imageAddress;
    }

}
