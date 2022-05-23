package com.apress.prospring5.ch16.controller;

import com.apress.prospring5.ch16.config.SpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@WebMvcTest({HelloController.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@WebAppConfiguration
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest({HelloController.class})
public class HelloControllerTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;

    //@Autowired
    //private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void sayHello() throws Exception {
//        String expectedResponse = "Welcome English";
//        MockHttpServletResponse response = mockMvc.perform(get("root/hello-world?language=en_US"))
//                .andReturn()
//                .getResponse();
//        System.out.println("MockHttpServletResponse response: " + response);
//        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void create() {
    }

    @Test
    void downloadPhoto() {
    }
}