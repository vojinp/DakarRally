package com.htec.vojinpesalj.dakarrally.integration;

import static com.htec.vojinpesalj.dakarrally.util.Utility.toJson;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.RegisterUserRequest;
import com.htec.vojinpesalj.dakarrally.util.Constants.FormValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.HeaderValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.Urls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTests {

    @Autowired private WebApplicationContext ctx;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    public void authenticate_ValidUsernameAndPassword_SuccessExpected() throws Exception {
        AuthenticateRequest authenticateRequest =
                AuthenticateRequest.builder()
                        .username(FormValues.USER_USERNAME)
                        .password(FormValues.USER_PASSWORD)
                        .build();

        mockMvc.perform(
                        post(Urls.AUTHORIZE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(authenticateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is(HeaderValues.BEARER)));
    }

    @Test
    public void authenticate_InvalidUsernameAndPassword_UnauthorizedExpected() throws Exception {
        AuthenticateRequest authenticateRequest =
                AuthenticateRequest.builder()
                        .username(FormValues.INVALID_USERNAME)
                        .password(FormValues.USER_PASSWORD)
                        .build();

        mockMvc.perform(
                        post(Urls.AUTHORIZE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(authenticateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void registerUser_ValidRequest_SuccessExpected() throws Exception {
        RegisterUserRequest registerUserRequest =
                RegisterUserRequest.builder()
                        .name(FormValues.NEW_NAME)
                        .username(FormValues.NEW_USERNAME)
                        .email(FormValues.NEW_EMAIL)
                        .password(FormValues.NEW_PASSWORD)
                        .build();

        mockMvc.perform(
                        post(Urls.REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(registerUserRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerUser_UsernameAlreadyExists_ConflictExpected() throws Exception {
        RegisterUserRequest registerUserRequest =
                RegisterUserRequest.builder()
                        .name(FormValues.NEW_NAME)
                        .username(FormValues.USER_USERNAME)
                        .email(FormValues.NEW_EMAIL)
                        .password(FormValues.NEW_PASSWORD)
                        .build();

        mockMvc.perform(
                        post(Urls.REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(registerUserRequest)))
                .andExpect(status().isConflict());
    }
}
