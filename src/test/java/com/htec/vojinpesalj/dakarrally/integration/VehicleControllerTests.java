package com.htec.vojinpesalj.dakarrally.integration;

import static com.htec.vojinpesalj.dakarrally.util.Utility.toJson;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.AuthenticateResponse;
import com.htec.vojinpesalj.dakarrally.service.dto.FilterKeys;
import com.htec.vojinpesalj.dakarrally.service.dto.FindVehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleRequest;
import com.htec.vojinpesalj.dakarrally.service.dto.VehicleTypeDto;
import com.htec.vojinpesalj.dakarrally.util.Constants.DatabaseIds;
import com.htec.vojinpesalj.dakarrally.util.Constants.FormValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.HeaderKeys;
import com.htec.vojinpesalj.dakarrally.util.Constants.HeaderValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.QueryKeys;
import com.htec.vojinpesalj.dakarrally.util.Constants.QueryValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.ResponseValues;
import com.htec.vojinpesalj.dakarrally.util.Constants.Urls;
import java.util.ArrayList;
import java.util.Collections;
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
public class VehicleControllerTests {

    @Autowired private WebApplicationContext ctx;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    @Transactional
    @Rollback
    public void update_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                        put(String.format(Urls.UPDATE_VEHICLE, DatabaseIds.VEHICLE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName", is(FormValues.NEW_TEAM_NAME)))
                .andExpect(jsonPath("$.model", is(FormValues.NEW_MODEL)));
    }

    @Test
    public void update_WithBadId_NotFoundExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                        put(String.format(Urls.UPDATE_VEHICLE, "999"))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithUserToken_ForbiddenExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                        put(String.format(Urls.UPDATE_VEHICLE, DatabaseIds.VEHICLE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    public void update_WithRunningRace_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                put(String.format(Urls.START_RACE, DatabaseIds.RACE_ID))
                        .header(HeaderKeys.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                        put(String.format(Urls.UPDATE_VEHICLE, DatabaseIds.VEHICLE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void delete_WithAdminToken_NoContentExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);

        mockMvc.perform(
                        delete(String.format(Urls.DELETE_VEHICLE, DatabaseIds.VEHICLE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findVehicle_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        FindVehicleRequest findVehicleRequest =
                FindVehicleRequest.builder()
                        .filterKeys(new ArrayList<>())
                        .filterValues(new ArrayList<>())
                        .filterOperations(new ArrayList<>())
                        .sortBy(FilterKeys.MODEL)
                        .build();

        mockMvc.perform(
                        put(Urls.FIND_VEHICLE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(findVehicleRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void findVehicle_WithUserToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);
        FindVehicleRequest findVehicleRequest =
                FindVehicleRequest.builder()
                        .filterKeys(new ArrayList<>())
                        .filterValues(new ArrayList<>())
                        .filterOperations(new ArrayList<>())
                        .sortBy(FilterKeys.MODEL)
                        .build();

        mockMvc.perform(
                        put(Urls.FIND_VEHICLE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(findVehicleRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void findVehicle_WithBadBody_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);
        FindVehicleRequest findVehicleRequest =
                FindVehicleRequest.builder()
                        .filterKeys(Collections.singletonList(FilterKeys.MODEL))
                        .filterValues(new ArrayList<>())
                        .filterOperations(new ArrayList<>())
                        .sortBy(FilterKeys.MODEL)
                        .build();

        mockMvc.perform(
                        put(Urls.FIND_VEHICLE)
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(findVehicleRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getStatistic_WithUserToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        get(String.format(Urls.GET_VEHICLE_STATISTIC, DatabaseIds.VEHICLE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ResponseValues.WORKING)));
    }

    @Test
    @Transactional
    @Rollback
    public void create_WithAdminToken_SuccessExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                        post(String.format(Urls.CREATE_VEHICLE, DatabaseIds.RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.teamName", is(FormValues.NEW_TEAM_NAME)))
                .andExpect(jsonPath("$.model", is(FormValues.NEW_MODEL)));
    }

    @Test
    public void create_WithRunningRace_BadRequestExpected() throws Exception {
        String token = getToken(FormValues.ADMIN_USERNAME, FormValues.ADMIN_PASSWORD);
        VehicleRequest vehicleRequest =
                VehicleRequest.builder()
                        .teamName(FormValues.NEW_TEAM_NAME)
                        .model(FormValues.NEW_MODEL)
                        .manufacturingDate(FormValues.NEW_MANUFACTURING_DATE)
                        .type(VehicleTypeDto.CROSS_MOTORCYCLE)
                        .build();

        mockMvc.perform(
                        post(String.format(Urls.CREATE_VEHICLE, DatabaseIds.RUNNING_RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(vehicleRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLeaderboard_WithType_SuccessExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        get(String.format(Urls.GET_LEADERBOARD, DatabaseIds.RACE_ID))
                                .param(QueryKeys.TYPE, QueryValues.SPORTS_CAR)
                                .header(HeaderKeys.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    public void getLeaderboard_WithoutType_SuccessExpected() throws Exception {
        String token = getToken(FormValues.USER_USERNAME, FormValues.USER_PASSWORD);

        mockMvc.perform(
                        get(String.format(Urls.GET_LEADERBOARD, DatabaseIds.RACE_ID))
                                .header(HeaderKeys.AUTHORIZATION, token))
                .andExpect(status().isOk());
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
