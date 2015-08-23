//package be.kdg.trips.controllers;
//
//import be.kdg.trips.config.AppConfig;
//import be.kdg.trips.config.SecurityConfig;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.FilterChainProxy;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static junit.framework.Assert.assertTrue;
//
///**
// * Created by Matthias on 23/08/2015.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AppConfig.class, SecurityConfig.class})
//@WebAppConfiguration
//public class ITappController {
//    @Autowired
//    private FilterChainProxy springSecurityFilter;
//    @Autowired
//    private WebApplicationContext context;
//    MockMvc mockMvc;
//    @Before
//    public void setUp() {
//         mockMvc= MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilter).build();
//
//    }
//
//    @Test
//    public void testHomeScreen() {
//        WebDriver driver = new HtmlUnitDriver();
//        driver.get("http://127.0.0.1:8080/Trips");
//        String title = driver.getTitle();
//        assertTrue(title.equals("Trips home"));
//
//    }
//
//
//
//}
