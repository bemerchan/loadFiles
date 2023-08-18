package com.upload.infraestructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoadRepositoryTest {
    private static final String URL_TEMPLATE = "/load";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_SIZE = "size";

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnRepositoryIndex() throws Exception {
        mvc.perform(get(URL_TEMPLATE)).andExpect(status().isOk());
    }

    @Test
    void customize() throws Exception {
        mvc.perform(get(URL_TEMPLATE)
                .param(PARAM_PAGE, anyString())
                .param(PARAM_SIZE, anyString())
        ).andExpect(status().isOk());
    }
}
