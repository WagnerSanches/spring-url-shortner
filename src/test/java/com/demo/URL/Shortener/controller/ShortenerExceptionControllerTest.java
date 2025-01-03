package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.exception.URLNotFoundException;
import com.demo.URL.Shortener.service.ShortenerService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ShortenerController.class, ShortenerExceptionController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShortenerExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortenerService shortenerService;

    @Test
    public void urlNotFoundException() throws Exception {
        given(this.shortenerService.getUrl(any(String.class))).willThrow(URLNotFoundException.class);
        ResultActions result = this.mockMvc.perform(get("/url-shortener/123")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ShortCode does not exist")));
    }

    @Test
    public void exceptionTest() throws Exception {
        given(this.shortenerService.getUrl(any(String.class))).willThrow(RuntimeException.class);

        ResultActions result = this.mockMvc.perform(get("/url-shortener/123")
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isInternalServerError());

    }


}
