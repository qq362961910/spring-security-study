package com.jy.study.spring.security.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//SpringRunner执行测试类
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "susan")
                .param("age", "18")
                .param("ageTo", "38")
                .param("xxx", "yyy")
//                .param("size", "15")
//                .param("page", "3")
//                .param("sort", "age,desc")
                //contentType: application/json
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //相应码200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //json path git hub project: https://github.com/json-path/JsonPath#Operators
                //集合长度为3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/123").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/aa").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+ System.currentTimeMillis() +"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+ (System.currentTimeMillis() + 100000) +"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
            .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello".getBytes())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
