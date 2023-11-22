package io.github.dsjdevelopment.voyaz.api.controller;

import io.github.dsjdevelopment.voyaz.api.domain.testimony.TestimonyDetailData;
import io.github.dsjdevelopment.voyaz.api.domain.testimony.TestimonyRegistrationData;
import io.github.dsjdevelopment.voyaz.api.domain.testimony.TestimonyService;
import io.github.dsjdevelopment.voyaz.api.domain.testimony.TestimonyUpdateData;
import jakarta.persistence.EntityNotFoundException;
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
class TestimonyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TestimonyRegistrationData> testimonyRegistrationDataJson;

    @Autowired
    private JacksonTester<TestimonyUpdateData> testimonyUpdateDataJson;

    @Autowired
    private JacksonTester<TestimonyDetailData> testimonyDetailDataJson;

    @MockBean
    private TestimonyService testimonyService;

    @Test
    @DisplayName("should return the http 400 code when the information is invalid")
    @WithMockUser
    void register_scenario1() throws Exception {
        var response = mvc
                .perform(post("/testimonials"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("should return the http 201 code when the information is valid")
    @WithMockUser
    void register_scenario2() throws Exception {

        var detailTestimonyDto = new TestimonyDetailData(1L, "i.jpg", "am", "strong");
        when(testimonyService.registerTestimony(any())).thenReturn(detailTestimonyDto);

        var response = mvc
                .perform(post("/testimonials")
                        .contentType("application/json")
                        .content(testimonyRegistrationDataJson.write(new TestimonyRegistrationData("i.jpg", "am", "strong")).getJson()))
                .andReturn().getResponse();
        var waitJson = testimonyDetailDataJson.write(detailTestimonyDto).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 200 code when the update is accepted")
    @WithMockUser
    void update_scenario1() throws Exception {

        var detailTestimonyDto = new TestimonyDetailData(1L, "i.jpg", "am", "strong");
        when(testimonyService.updateTestimony(any())).thenReturn(detailTestimonyDto);

        var response = mvc
                .perform(put("/testimonials")
                        .contentType("application/json")
                        .content(testimonyUpdateDataJson.write(new TestimonyUpdateData(1L, "i.jpg", "am", "strong")).getJson()))
                .andReturn().getResponse();

        var waitJson = testimonyDetailDataJson.write(detailTestimonyDto).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the update is rejected")
    @WithMockUser
    void update_scenario2() throws Exception {

        when(testimonyService.updateTestimony(any())).thenThrow(new EntityNotFoundException());

        var response = mvc
                .perform(put("/testimonials")
                        .contentType("application/json")
                        .content(testimonyUpdateDataJson.write(new TestimonyUpdateData(1L, "i.jpg", "am", "strong")).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 204 code when the delete is accepted")
    @WithMockUser
    void delete_scenario1() throws Exception {
        var detailTestimonyDto = new TestimonyDetailData(1L, "i.jpg", "am", "strong");
        when(testimonyService.registerTestimony(any())).thenReturn(detailTestimonyDto);

        var response = mvc
                .perform(delete("/testimonials/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("should return the http 404 code when the delete is rejected")
    @WithMockUser
    void delete_scenario2() throws Exception {

        when(testimonyService.deleteTestimony(any())).thenThrow(new EntityNotFoundException());

        var response = mvc
                .perform(delete("/testimonials/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 200 code when the detail is accepted")
    @WithMockUser
    void detail_scenario1() throws Exception {

        var detailTestimonyDto = new TestimonyDetailData(1L, "i.jpg", "am", "strong");
        when(testimonyService.detailTestimony(any())).thenReturn(detailTestimonyDto);

        var response = mvc
                .perform(get("/testimonials/1"))
                .andReturn().getResponse();

        var waitJson = testimonyDetailDataJson.write(detailTestimonyDto).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the detail is rejected")
    @WithMockUser
    void detail_scenario2() throws Exception {

        when(testimonyService.detailTestimony(any())).thenThrow(new EntityNotFoundException());

        var response = mvc
                .perform(get("/testimonials/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


}





