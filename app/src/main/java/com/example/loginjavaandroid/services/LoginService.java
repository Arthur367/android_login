package com.example.loginjavaandroid.services;


import com.example.loginjavaandroid.requests.LoginRequest;
import com.example.loginjavaandroid.responses.LoginResponse;


import retrofit2.Call;

import retrofit2.http.POST;

public interface LoginService {

    @POST("/BC200/ODataV4/Company(%27MOBILETEST%27)/QYLTRLogins")
    Call<LoginResponse> userLogin();
}
