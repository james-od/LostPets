package com.example.master.LostPets;

/**
 * Created by Master on 08-Oct-16.
 */

//**************************************************************************************************
//CLASS FOR THE TEXT INFORMATION
//**************************************************************************************************
public class GPSTestResultsArr {
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
