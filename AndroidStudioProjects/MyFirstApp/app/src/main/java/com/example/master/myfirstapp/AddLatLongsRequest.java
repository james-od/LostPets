package com.example.master.myfirstapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master on 24-Sep-16.
 */
public class AddLatLongsRequest extends StringRequest{

    private static final String ADDLATLONGS_REQUEST_URL = "http://james-odonnell.com/AddLatLongs.php";
    private Map<String, String> params;

    public AddLatLongsRequest(double latitude, double longitude, int id, String name, Response.Listener<String> listener){

        super(Request.Method.POST, ADDLATLONGS_REQUEST_URL,listener,null);
        //null is error listener
        params = new HashMap<>();
        params.put("latitude",latitude+"");
        params.put("longitude",longitude+"");
        params.put("id",id + "");
        params.put("name",name);
    }

    @Override
    public Map<String, String> getParams() {
        System.out.println("get Params, AddLL");
        return params;
    }
}
