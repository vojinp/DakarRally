package com.htec.vojinpesalj.dakarrally.web.controller;

import com.htec.vojinpesalj.dakarrally.service.UserService;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.RegisterUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(
        value = "Auth Management System",
        description = "Operations pertaining to user in Auth Management System")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    @ApiOperation(value = "Log in", response = AuthenticateResponse.class)
    public ResponseEntity<AuthenticateResponse> authenticate(
            @ApiParam(value = "Username and password", required = true) @Valid @RequestBody
                    AuthenticateRequest user) {
        return ResponseEntity.ok(userService.authenticateUser(user));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Register new user")
    public ResponseEntity registerUser(
            @ApiParam(value = "User information", required = true) @Valid @RequestBody
                    RegisterUserRequest user) {
        userService.registerUser(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
