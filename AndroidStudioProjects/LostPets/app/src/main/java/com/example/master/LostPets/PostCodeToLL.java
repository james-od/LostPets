package com.example.master.LostPets;

/**
 * Created by Master on 16-Jan-17.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;


public class PostCodeToLL extends StringRequest {

    private Map<String, String> params;

    public PostCodeToLL(String postCode, String[] arr, Response.Listener<String> listener){

        super(Request.Method.POST, "https://maps.googleapis.com/maps/api/geocode/json?address="+postCode+"&key=AIzaSyDUE2xyKNhNpF6_gidv0ISCF0JDLh4ID8A",listener,null);
        //null is error listener
        params = new HashMap<>();
        params.put("array",arr+"");
    }

    @Override
    public Map<String, String> getParams() {
        System.out.println("get Params, PCtoLL");
        return params;
    }
}
