package com.sparta.mdesigns.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SecurityContext securityContext;


    @Test
    public void ShowHomePageTest_UserNotAuthenticated() throws Exception {
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");
        //ModelAndViewAssert.assertModelAttributeAvailable(mav,"session");

    }

    @Test
    public void ShowHomePageTest_UserAuthenticated() throws Exception {

        Authentication authentication=new UsernamePasswordAuthenticationToken("testUser","testPassword");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");
        ModelAndViewAssert.assertModelAttributeAvailable(mav,"session");

    }

    @Test
    public void showLoginPageTest() throws Exception {

        //Checking the view name
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/showlogin"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"login");

        //Checking the Session Attribute
        HttpSession session=mockMvc.perform(MockMvcRequestBuilders.get("/showlogin").header("Referer","myHeader"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getRequest().getSession();
        Assertions.assertEquals("myHeader",session.getAttribute("url_prior_login"));

    }

}
