package com.example.demo.logs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {

    private Logger() {
        throw new IllegalStateException("Utility class");
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void trace(String message) {
        log.trace(message);
    }
    
}
