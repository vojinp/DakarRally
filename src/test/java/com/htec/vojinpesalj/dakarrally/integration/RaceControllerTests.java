package com.htec.vojinpesalj.dakarrally.integration;

import static com.htec.vojinpesalj.dakarrally.util.Utility.toJson;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateResponse;
import com.htec.vojinpesalj.dakarrally.util.Constants.DatabaseIds;
import com.htec.vojinpesalj.dakarrally.util.Constants.FormValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.HeaderKeys;
import com.htec.vojinpesalj.dakarrally.util.Constants.HeaderValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.QueryKeys;
import com.htec.vojinpesalj.dakarrally.util.Constants.QueryValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.ResponseValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.Urls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RaceControllerTests {

    @Autowired private WebApplicationContext ctx;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    public void create_WithoutToken_UnauthorizedExpected() throws Exception {
        mockMvc.perform(
                        post(Urls.CREATE_RACE)
                                .param(QueryKeys.YEAR, QueryValues.YEAR)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    public void create_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        post(Urls.CREATE_RACE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .param(QueryKeys.YEAR, QueryValues.YEAR)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.year", is(Integer.valueOf(QueryValues.YEAR))));
    }

    @Test
    public void create_WithoutYearParameter_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        post(Urls.CREATE_RACE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_WithUserToken_ForbiddenExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        post(Urls.CREATE_RACE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .param(QueryKeys.YEAR, QueryValues.YEAR)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    public void startRace_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        put(String.format(Urls.START_RACE, DatabaseIds.RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @Rollback
    public void startRace_WithBadId_NotFoundExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
            put(String.format(Urls.START_RACE, "999"))
                .header(HeaderKeys.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void startRace_WithoutToken_UnauthorizedExpected() throws Exception {
        mockMvc.perform(
                        put(String.format(Urls.START_RACE, DatabaseIds.RACE_ID))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void startRace_WithUserToken_ForbiddenExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        put(String.format(Urls.START_RACE, DatabaseIds.RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void startRace_WithRunningRace_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        put(String.format(Urls.START_RACE, DatabaseIds.RUNNING_RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void startRace_WithFinishedRace_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        put(String.format(Urls.START_RACE, DatabaseIds.FINISHED_RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getStatistic_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        get(String.format(Urls.GET_RACE_STATISTIC, DatabaseIds.RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseValues.PENDING)));
    }

    @Test
    public void getStatistic_WithUserToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        get(String.format(Urls.GET_RACE_STATISTIC, DatabaseIds.RUNNING_RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseValues.RUNNING)));
    }

    private String getToken(String username, String password) throws Exception {
        AuthenticateRequest authenticateRequest =
                AuthenticateRequest.builder().username(username).password(password).build();

        String responseString =
                mockMvc.perform(
                                post(Urls.AUTHORIZE)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(toJson(authenticateRequest)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        return String.format(
                "%s %s",
                HeaderValues.BEARER,
                new ObjectMapper()
                        .readValue(responseString, AuthenticateResponse.class)
                        .getToken());
    }
}
