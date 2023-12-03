package io.github.dsjdevelopment.voyaz.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyDetailData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyListData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyRegistrationData;
import io.github.dsjdevelopment.voyaz.api.domain.destiny.DestinyUpdateData;
import io.github.dsjdevelopment.voyaz.api.infra.exception.ExceptionValidation;
import io.github.dsjdevelopment.voyaz.api.service.DestinyService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DestinyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DestinyRegistrationData> destinyRegistrationDataJson;

    @Autowired
    private JacksonTester<DestinyUpdateData> destinyUpdateDataJson;

    @Autowired
    private JacksonTester<DestinyDetailData> destinyDetailDataJson;

    @Autowired
    private JacksonTester<Page<DestinyListData>> destinyListDataJson;

    @MockBean
    private DestinyService destinyService;

    private DestinyDetailData destinyDetailData;

    @BeforeEach
    void setUp() {
        destinyDetailData = new DestinyDetailData(1L, "i.jpg", "am", new BigDecimal(1200));
        when(destinyService.registerDestiny(any())).thenReturn(destinyDetailData);
        when(destinyService.updateDestiny(any())).thenReturn(destinyDetailData);
        when(destinyService.detailDestiny(any())).thenReturn(destinyDetailData);
    }

    @Test
    @DisplayName("should return the http 400 code when the information is invalid")
    @WithMockUser
    void register_scenario1() throws Exception {
        var response = mvc
                .perform(post("/destinations"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("should return the http 201 code when the information is valid")
    @WithMockUser
    void register_scenario2() throws Exception {

        var response = mvc
                .perform(post("/destinations")
                        .contentType("application/json")
                        .content(destinyRegistrationDataJson.write(new DestinyRegistrationData("i.jpg", "am", new BigDecimal(1200))).getJson()))
                .andReturn().getResponse();
        var waitJson = destinyDetailDataJson.write(destinyDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 200 code when the update is accepted")
    @WithMockUser
    void update_scenario1() throws Exception {

        var response = mvc
                .perform(put("/destinations")
                        .contentType("application/json")
                        .content(destinyUpdateDataJson.write(new DestinyUpdateData(1L, "i.jpg", "am", new BigDecimal(1200))).getJson()))
                .andReturn().getResponse();

        var waitJson = destinyDetailDataJson.write(destinyDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the update is rejected")
    @WithMockUser
    void update_scenario2() throws Exception {

        doThrow(EntityNotFoundException.class).when(destinyService).updateDestiny(any());

        var response = mvc
                .perform(put("/destinations")
                        .contentType("application/json")
                        .content(destinyUpdateDataJson.write(new DestinyUpdateData(1L, "i.jpg", "am", new BigDecimal(1200))).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 204 code when the delete is accepted")
    @WithMockUser
    void delete_scenario1() throws Exception {



        var response = mvc
                .perform(delete("/destinations/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("should return the http 404 code when the delete is rejected")
    @WithMockUser
    void delete_scenario2() throws Exception {

        doThrow(EntityNotFoundException.class).when(destinyService).deleteDestiny(any());

        var response = mvc
                .perform(delete("/destinations/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 200 code when the detail is accepted")
    @WithMockUser
    void detail_scenario1() throws Exception {

        var response = mvc
                .perform(get("/destinations/1"))
                .andReturn().getResponse();

        var waitJson = destinyDetailDataJson.write(destinyDetailData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(waitJson);
    }

    @Test
    @DisplayName("should return the http 404 code when the detail is rejected")
    @WithMockUser
    void detail_scenario2() throws Exception {

        doThrow(EntityNotFoundException.class).when(destinyService).detailDestiny(any());

        var response = mvc
                .perform(get("/destinations/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("should return the http 200 code when the pagination is accepted")
    @WithMockUser
    void page_scenario1() throws Exception {

        List<DestinyListData> destinyList = List.of(new DestinyListData(1L, "i.jpg", "am", new BigDecimal(1200)));
        var destinyPage = new PageImpl<>(destinyList);
        when(destinyService.search(any(), any())).thenReturn(destinyPage);

        var response = mvc
                .perform(get("/destinations?name=am"))
                .andReturn().getResponse();

        ObjectMapper objectMapper = new ObjectMapper();
        var waitJson = objectMapper.readTree(destinyListDataJson.write(destinyPage).getJson());
        var responseJson = objectMapper.readTree(response.getContentAsString());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseJson).isEqualTo(waitJson);

    }

    @Test
    @DisplayName("should return the http 400 code when the page is rejected")
    @WithMockUser
    void page_scenario2() throws Exception {

        when(destinyService.search(any(), any())).thenThrow(new ExceptionValidation("No destinations found"));

        var response = mvc
                .perform(get("/destinations?name=am"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"Message\":\"No destinations found\"}");

    }
}





