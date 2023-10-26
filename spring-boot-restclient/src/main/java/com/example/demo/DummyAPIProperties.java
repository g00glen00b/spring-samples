package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "dummy-api")
public record DummyAPIProperties(@DefaultValue("https://dummyjson.com") String url) {
}
