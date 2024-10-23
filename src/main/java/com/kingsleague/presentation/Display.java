package com.kingsleague.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Display {
    private static final Logger LOGGER = LoggerFactory.getLogger(Display.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Starting E-Sports Tournament Management System");

        org.h2.tools.Server h2WebServer = context.getBean("h2WebServer", org.h2.tools.Server.class);
        String h2ConsoleUrl = h2WebServer.getURL();
        LOGGER.info("H2 Console URL: {}", h2ConsoleUrl);

        LOGGER.info("E-Sports Tournament Management System terminated");
    }
}