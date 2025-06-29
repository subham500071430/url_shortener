package com.app.URLShortener.util.logging;

public enum LogLevel {

       INFO(2),
       WARN(3),
       ERROR(4),
       DEBUG(1);

       private final int level;

       LogLevel(int level) {
              this.level = level;
       }

       public int getLevel() {
              return level;
       }

       public static LogLevel fromString(String value) {
              return LogLevel.valueOf(value.trim().toUpperCase());
       }
}
