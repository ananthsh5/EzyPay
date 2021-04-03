package com.app.ezypay.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.app.ezypay.EzyPayAppLauncher;

/**
 * @author Ananth Shanmugam
 * Class to define configuration for spring boot
 */
@SpringBootApplication
public class EzyPayApplicationInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EzyPayAppLauncher.class);
    }
}
