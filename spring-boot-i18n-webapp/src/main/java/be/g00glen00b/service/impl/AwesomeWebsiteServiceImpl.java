package be.g00glen00b.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AwesomeWebsiteServiceImpl {
    private String[] awesomeWebsites = new String[] { "http://g00glen00b.be", "http://start.spring.io" };
    @Autowired
    private Random random;

    public String getAwesomeWebsite() {
        return awesomeWebsites[random.nextInt(awesomeWebsites.length)];
    }
}
