package com.example.master.myfirstapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master on 24-Sep-16.
 */
public class GetLatLongsRequest extends StringRequest{

    private static final String GETLATLONGS_REQUEST_URL = "http://james-odonnell.com/DogsLostRetrieval.php"; //1!2@3#4$
    private Map<String, String> params;

    public GetLatLongsRequest(String[] arr, Response.Listener<String> listener){

        super(Request.Method.POST, GETLATLONGS_REQUEST_URL,listener,null);
        //null is error listener
        params = new HashMap<>();
        params.put("array",arr+"");

    }

    @Override
    public Map<String, String> getParams() {
        System.out.println("get Params, GetLL");
        return params;
    }
}
