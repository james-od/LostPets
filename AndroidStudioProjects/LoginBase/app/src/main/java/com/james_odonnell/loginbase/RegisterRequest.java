package com.james_odonnell.loginbase;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master on 17-Sep-16.
 */
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://james-odonnell.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        //null is error listener
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("age",age + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
