package com.upload.application.api;

import com.jayway.jsonpath.JsonPath;
import com.upload.domain.core.MessageUtil;
import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;
import com.upload.infraestructure.repository.LoadDataRepository;
import com.upload.infraestructure.repository.LoadRepository;
import org.apache.poi.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoadDataRepository loadDataRepository;


    @Autowired
    private LoadRepository loadRepository;

    @Test
    void loadFile() throws Exception {
        File file = new File("src/test/resources/technical_challenge_data.csv");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "csv", IOUtils.toByteArray(input));

       MockMultipartHttpServletRequestBuilder multipartRequest
                = MockMvcRequestBuilders.multipart("/upload");

        MvcResult mvcResult = mockMvc.perform(multipartRequest.file(multipartFile))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        Thread.sleep(2000);

        List<LoadData> detail = loadDataRepository.findByLoadId(id.longValue());
        Load load = loadRepository.findById(id.longValue()).get();
        assertTrue(detail.size() > 0);
        assertEquals("EXITOSO", load.getStatus());
    }

    @Test
    void loadWhenFileExtensionError() throws Exception {
        File file = new File("src/test/resources/logo.png");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "png", IOUtils.toByteArray(input));

        MockMultipartHttpServletRequestBuilder multipartRequest
                = MockMvcRequestBuilders.multipart("/upload");
        try {
            mockMvc.perform(multipartRequest.file(multipartFile))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            assertEquals(MessageUtil.INCORRECT_FILE_FORMAT, e.getCause().getMessage());
        }

    }
}
