package com.ladyshopee.api.service;

import com.ladyshopee.api.model.User;
import com.ladyshopee.api.payload.request.LoginRequest;
import com.ladyshopee.api.payload.request.RegisterRequest;
import com.ladyshopee.api.payload.response.Response;

public interface AuthenticationService {
    Response register(RegisterRequest request);

    User login(LoginRequest request);
}
