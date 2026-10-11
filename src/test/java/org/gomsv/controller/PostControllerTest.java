package org.gomsv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPostReturns201() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"테스트 제목","content":"테스트 내용"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    void blankTitleReturns400() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"","content":"내용"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void missingPostReturns404() throws Exception {
        mockMvc.perform(get("/api/posts/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void simulate503ReturnsServiceUnavailable() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .header("X-Simulate-Error", "503"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.statusCode").value(503));
    }
}
