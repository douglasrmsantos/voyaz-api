package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.user.UserDetailData;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserRegistrationData;
import io.github.dsjdevelopment.voyaz.api.service.UserService;
import io.github.dsjdevelopment.voyaz.api.domain.user.UserUpdateData;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserRegistrationData> userRegistrationDataJson;

    @Autowired
    private JacksonTester<UserUpdateData> userUpdateDataJson;

    @Autowired
    private JacksonTester<UserDetailData> userDetailDataJson;

    @MockBean
    private UserService userService;

    private UserDetailData userDetailData;

    @BeforeEach
    void setUp() {
        userDetailData = new UserDetailData(1L, "iamstrong@voyaz.api", true);
    }
    @Test
    @DisplayName("should return the http 400 code when the information is invalid")
    @WithMockUser
    void register_scenario1() throws Exception {
        var response = mvc
                .perform(post("/users"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("should return the http 201 code when the information is valid")
    @WithMockUser
    void register_scenario2() throws Exception {
        var userDetailData = new UserDetailData(null, "iamstrong@voyaz.api", true);
        when(userService.registerUser(any(UserRegistrationData.class))).thenReturn(userDetailData);

        var response = mvc
                .perform(post("/users")
                        .contentType("application/json")
                        .content(userRegistrationDataJson.write(new UserRegistrationData("iamstrong@voyaz.api", "123456")).getJson()))
                .andReturn().getResponse();

        var waitJson = userDetailDataJson.write(userDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 200 code when the update is accepted")
    @WithMockUser
    void update_scenario1() throws Exception {

        var userDetailData = new UserDetailData(1L, "iamstrong@voyaz.api", true);
        when(userService.updateUser(any())).thenReturn(userDetailData);

        var response = mvc
                .perform(put("/users")
                        .contentType("application/json")
                        .content(userUpdateDataJson.write(new UserUpdateData(1L, "iamstrong@voyaz.api", "123456")).getJson()))
                .andReturn().getResponse();

        var waitJson = userDetailDataJson.write(userDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the update is rejected")
    @WithMockUser
    void update_scenario2() throws Exception {

    when(userService.updateUser(any())).thenThrow(new EntityNotFoundException("User not found in database: " + 1L));

        var response = mvc
                .perform(put("/users")
                        .contentType("application/json")
                        .content(userUpdateDataJson.write(new UserUpdateData(1L, "iamstrong@voyaz.api", "123456")).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 204 code when the delete is accepted")
    @WithMockUser
    void delete_scenario1() throws Exception {

        var response = mvc
                .perform(delete("/users/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("should return the http 404 code when the delete is rejected")
    @WithMockUser
    void delete_scenario2() throws Exception {

        when(userService.deleteUser(any())).thenThrow(new EntityNotFoundException("User not found in database: " + 1L));

        var response = mvc
                .perform(delete("/users/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 200 code when the detail is accepted")
    @WithMockUser
    void detail_scenario1() throws Exception {

        when(userService.detailUser(any())).thenReturn(userDetailData);

        var response = mvc
                .perform(get("/users/1"))
                .andReturn().getResponse();

        var waitJson = userDetailDataJson.write(userDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the detail is rejected")
    @WithMockUser
    void detail_scenario2() throws Exception {

        when(userService.detailUser(any())).thenThrow(new EntityNotFoundException("User not found in database: " + 1L));

        var response = mvc
                .perform(get("/users/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


}





