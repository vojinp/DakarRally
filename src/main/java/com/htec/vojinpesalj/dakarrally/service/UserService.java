package com.htec.vojinpesalj.dakarrally.service;

import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RegisterUserRequest;

public interface UserService {

    /**
     * Authenticating the user
     *
     * @param authenticateRequest username and password information
     * @return token and token type
     */
    AuthenticateResponse authenticateUser(AuthenticateRequest authenticateRequest);

    /**
     * Registering new user
     *
     * @param user information of the new user
     */
    void registerUser(RegisterUserRequest user);
}
