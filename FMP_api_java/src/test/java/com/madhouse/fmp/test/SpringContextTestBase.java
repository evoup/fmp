package com.madhouse.fmp.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.madhouse.fmp.config.MainConfig;
import com.madhouse.fmp.config.SocialConfig;
import com.madhouse.fmp.config.SpringMvcInitializer;
import com.madhouse.fmp.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/applicationContext.xml"}) 
@ContextConfiguration(classes = {MainConfig.class, SocialConfig.class, WebMvcConfig.class ,SpringMvcInitializer.class/** SecurityConfig.class */})
@WebAppConfiguration
public class SpringContextTestBase {
}
